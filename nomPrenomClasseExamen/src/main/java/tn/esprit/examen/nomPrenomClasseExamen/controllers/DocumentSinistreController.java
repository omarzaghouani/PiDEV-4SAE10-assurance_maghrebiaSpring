package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.DocumentSinistre;
import tn.esprit.examen.nomPrenomClasseExamen.services.DocumentSinistreService;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentSinistreController {

    private final DocumentSinistreService documentSinistreService;

    public DocumentSinistreController(DocumentSinistreService documentSinistreService) {
        this.documentSinistreService = documentSinistreService;
    }

    @PostMapping("/add")
    public DocumentSinistre addDocument(@RequestBody DocumentSinistre documentSinistre) {
        return documentSinistreService.createDocument(documentSinistre);
    }

    @GetMapping("/list")
    public List<DocumentSinistre> listDocuments() {
        return documentSinistreService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public DocumentSinistre getDocument(@PathVariable int id) {
        return documentSinistreService.getDocumentById(id);
    }

    @PutMapping("/{id}")
    public DocumentSinistre updateDocument(@PathVariable int id, @RequestBody DocumentSinistre documentSinistre) {
        return documentSinistreService.updateDocument(id, documentSinistre);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable int id) {
        documentSinistreService.deleteDocument(id);
    }
}
