package tn.esprit.examen.nomPrenomClasseExamen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FinancialManagement;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FinancialManagementRepository;
import tn.esprit.examen.nomPrenomClasseExamen.service.FinancialManagementService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/financial")
@RequiredArgsConstructor
public class FinancialManagementController {

    private final FinancialManagementService financialManagementService;
    private final FinancialManagementRepository financialManagementRepository;

    // Ajouter un nouvel enregistrement financier
    @PostMapping("/add")
    public ResponseEntity<FinancialManagement> addFinancialRecord(@RequestBody FinancialManagement financialManagement) {
        FinancialManagement savedRecord = financialManagementService.addFinancialRecord(financialManagement);
        return ResponseEntity.ok(savedRecord);
    }

    // Récupérer tous les enregistrements financiers
    @GetMapping("/all")
    public ResponseEntity<List<FinancialManagement>> getAllFinancialRecords() {
        return ResponseEntity.ok(financialManagementService.getAllFinancialRecords());
    }

    // Récupérer un enregistrement financier par ID
    @GetMapping("/{id}")
    public ResponseEntity<FinancialManagement> getFinancialRecordById(@PathVariable int id) {
        Optional<FinancialManagement> record = financialManagementService.getFinancialRecordById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un enregistrement financier
    @PutMapping("/{id}")
    public ResponseEntity<FinancialManagement> updateFinancialRecord(@PathVariable int id, @RequestBody FinancialManagement updatedRecord) {
        try {
            FinancialManagement updated = financialManagementService.updateFinancialRecord(id, updatedRecord);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un enregistrement financier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancialRecord(@PathVariable int id) {
        try {
            financialManagementService.deleteFinancialRecord(id);
            return ResponseEntity.noContent().build(); // ✅ Retourne un succès HTTP 204
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // ❌ Retourne un 404 si l'élément n'existe pas
        }
    }

    @GetMapping("/export")
    public List<FinancialManagement> exportData() {
        return financialManagementRepository.findAll();
    }
    @GetMapping("/predict")
    public ResponseEntity<?> getPrediction() {
        String result = "";
        try {
            List<FinancialManagement> data = financialManagementRepository.findAll();
            result = financialManagementService.runPredictionScript(data);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(result, Map.class);
            System.out.println("👌👌👌 Résultat brut du script Python = " + result);

            return ResponseEntity.ok(jsonMap);

        } catch (Exception e) {
            System.out.println("❌ Erreur - Résultat brut du script Python = " + result);
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur serveur : " + e.getMessage());
        }
    }





    // @Scheduled(cron = "0 0 8 * * MON") // tous les lundis à 8h
    @Scheduled(cron = "0 * * * * *")
    public void refreshPrediction() throws IOException {
        getPrediction(); // ou stocker le résultat en base

    }

}
