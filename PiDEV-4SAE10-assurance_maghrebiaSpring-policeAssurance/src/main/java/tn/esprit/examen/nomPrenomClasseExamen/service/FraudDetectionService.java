package tn.esprit.examen.nomPrenomClasseExamen.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class FraudDetectionService {

    public static FraudResult predictFraud(int userId, int orderId, double amount, int reasonCode, int refundCount, int previousFrauds) {
        try {
            // ✅ Dynamically get the relative path to Python script
            String pythonPath = "python"; // ✅ Use "python" instead of absolute path
            String scriptPath = new File("PiDEV-4SAE10-assurance_maghrebiaSpring-policeAssurance/src/main/resources/python/fraud_detection.py").getAbsolutePath();

            // ✅ Debug Info
            System.out.println("🔍 ML INPUTS: userId=" + userId + ", orderId=" + orderId + ", amount=" + amount +
                    ", reasonCode=" + reasonCode + ", refundCount=" + refundCount +
                    ", previousFrauds=" + previousFrauds);

            // ✅ Create Process
            ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath,
                    String.valueOf(userId), String.valueOf(orderId), String.valueOf(amount),
                    String.valueOf(reasonCode), String.valueOf(refundCount),
                    String.valueOf(previousFrauds));

            processBuilder.redirectErrorStream(true); // ✅ Capture errors
            Process process = processBuilder.start();

            // ✅ Capture Python Output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            if (line != null) {
                System.out.println("🔍 ML OUTPUT: " + line);
                String[] results = line.split(",");
                boolean isFraud = Integer.parseInt(results[0]) == 1;
                double probability = Double.parseDouble(results[1]);
                return new FraudResult(isFraud, probability);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("❌ ERROR: ML model did not return valid output.");
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
