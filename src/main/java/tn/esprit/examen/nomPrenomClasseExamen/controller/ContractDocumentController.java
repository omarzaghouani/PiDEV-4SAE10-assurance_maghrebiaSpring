package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractDocument;
import tn.esprit.examen.nomPrenomClasseExamen.service.IContractDocumentService;

import java.util.List;

@RestController
@RequestMapping("/contract-documents")

public class ContractDocumentController {
    @Autowired
     IContractDocumentService contractDocumentService;

    @PostMapping("/add")
    public ResponseEntity<ContractDocument> addContractDocument(@RequestBody ContractDocument document) {
        ContractDocument savedDocument = contractDocumentService.addContractDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDocument> getContractDocument(@PathVariable Long id) {
        ContractDocument contractDocument = contractDocumentService.getContractDocumentById(id);
        return contractDocument != null ? ResponseEntity.ok(contractDocument) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContractDocument>> getAllContractDocuments() {
        return ResponseEntity.ok(contractDocumentService.getAllContractDocuments());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ContractDocument> updateContractDocument(
            @PathVariable Long id, @RequestBody ContractDocument contractDocument) {
        ContractDocument updatedDoc = contractDocumentService.updateContractDocument(id, contractDocument);
        return (updatedDoc != null) ? ResponseEntity.ok(updatedDoc) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteContractDocument(@PathVariable Long id) {
        contractDocumentService.deleteContractDocument(id);
        return ResponseEntity.noContent().build();
    }
}
