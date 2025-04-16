package tn.esprit.examen.nomPrenomClasseExamen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FinancialManagement;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FinancialManagementRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinancialManagementService implements IFinancialManagementService {

    private final FinancialManagementRepository financialManagementRepository;

    @Override
    public FinancialManagement addFinancialRecord(FinancialManagement financialManagement) {
        return financialManagementRepository.save(financialManagement);
    }

    @Override
    public List<FinancialManagement> getAllFinancialRecords() {
        return financialManagementRepository.findAll();
    }

    @Override
    public Optional<FinancialManagement> getFinancialRecordById(int id) {
        return financialManagementRepository.findById(id);
    }

    @Override
    public FinancialManagement updateFinancialRecord(int id, FinancialManagement updatedRecord) {
        return financialManagementRepository.findById(id).map(record -> {
            record.setTotalRevenue(updatedRecord.getTotalRevenue());
            record.setExpenses(updatedRecord.getExpenses());
            record.setNetIncome(updatedRecord.getNetIncome());
            return financialManagementRepository.save(record);
        }).orElseThrow(() -> new RuntimeException("Financial record not found with ID: " + id));
    }

    @Override
    public void deleteFinancialRecord(int id) {
        //if (!financialManagementRepository.existsById(id)) {
            //throw new RuntimeException("Financial record not found with ID: " + id);
        //}
        financialManagementRepository.deleteById(id);
    }

    public String runPredictionScript(List<FinancialManagement> data) throws IOException {
        System.out.println("📤 [INFO] Début exécution du script Python");

        // Convertir les données en JSON
        String jsonData = new ObjectMapper().writeValueAsString(data);
        String safeJson = jsonData.replace("\"", "\\\"").replace("\n", " ");

        File scriptFile = new File("PiDEV-4SAE10-assurance_maghrebiaSpring-policeAssurance/src/main/resources/python/predictor.py");
        String scriptPath = scriptFile.getAbsolutePath();

        System.out.println("📁 [DEBUG] Chemin absolu script : " + scriptPath);

        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, safeJson);
        System.out.println("😒😒 [DEBUG] Chemin absolu script : " + scriptPath+"   " +safeJson);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        // Attente de fin de process avec timeout
        try {
            if (!process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)) {
                process.destroy();
                throw new RuntimeException("⏱️ Timeout : le script Python a mis trop de temps à répondre");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            process.destroy();
        }

        System.out.println("✅ [SUCCESS] Résultat brut : " + output.toString());

        return output.toString();
    }


}
