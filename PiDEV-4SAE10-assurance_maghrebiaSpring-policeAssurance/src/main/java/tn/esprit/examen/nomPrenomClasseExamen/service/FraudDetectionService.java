package tn.esprit.examen.nomPrenomClasseExamen.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FraudDetectionService {



    public static FraudResult predictFraud(int userId, int orderId, double amount, int reasonCode, int refundCount, int previousFrauds, double riskScore, int actionTaken) {
        try {
            String pythonScriptPath = "src/main/resources/python/fraud_detection.py";
            ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScriptPath,
                    String.valueOf(userId), String.valueOf(orderId), String.valueOf(amount),
                    String.valueOf(reasonCode), String.valueOf(refundCount),
                    String.valueOf(previousFrauds), String.valueOf(riskScore), String.valueOf(actionTaken));

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                String[] results = line.split(",");
                boolean isFraud = Integer.parseInt(results[0]) == 1;
                double probability = Double.parseDouble(results[1]);
                return new FraudResult(isFraud, probability);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FraudResult(false, 0.0);
    }

    public static class FraudResult {
        public boolean isFraud;
        public double probability;

        public FraudResult(boolean isFraud, double probability) {
            this.isFraud = isFraud;
            this.probability = probability;
        }
    }

}
