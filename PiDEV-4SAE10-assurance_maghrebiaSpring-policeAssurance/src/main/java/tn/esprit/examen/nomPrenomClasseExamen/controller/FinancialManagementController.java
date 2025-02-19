package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FinancialManagement;
import tn.esprit.examen.nomPrenomClasseExamen.service.FinancialManagementService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/financial")
@RequiredArgsConstructor
public class FinancialManagementController {

    private final FinancialManagementService financialManagementService;

    // Ajouter un nouvel enregistrement financier
    @PostMapping
    public ResponseEntity<FinancialManagement> addFinancialRecord(@RequestBody FinancialManagement financialManagement) {
        FinancialManagement savedRecord = financialManagementService.addFinancialRecord(financialManagement);
        return ResponseEntity.ok(savedRecord);
    }

    // Récupérer tous les enregistrements financiers
    @GetMapping
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
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
