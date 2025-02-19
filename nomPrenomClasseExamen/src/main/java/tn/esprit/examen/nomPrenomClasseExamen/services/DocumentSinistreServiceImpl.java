package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.DocumentSinistre;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.DocumentSinistreRepository;


import java.util.List;
import java.util.Optional;

@Service
public class DocumentSinistreServiceImpl implements DocumentSinistreService {

    private final DocumentSinistreRepository documentSinistreRepository;

    public DocumentSinistreServiceImpl(DocumentSinistreRepository documentSinistreRepository) {
        this.documentSinistreRepository = documentSinistreRepository;
    }

    @Override
    public DocumentSinistre createDocument(DocumentSinistre documentSinistre) {
        return documentSinistreRepository.save(documentSinistre);
    }

    @Override
    public List<DocumentSinistre> getAllDocuments() {
        return documentSinistreRepository.findAll();
    }

    @Override
    public DocumentSinistre getDocumentById(int id) {
        return documentSinistreRepository.findById(id).orElse(null);
    }

    @Override
    public DocumentSinistre updateDocument(int id, DocumentSinistre documentSinistre) {
        Optional<DocumentSinistre> existingDocument = documentSinistreRepository.findById(id);
        if (existingDocument.isPresent()) {
            documentSinistre.setId(id);
            return documentSinistreRepository.save(documentSinistre);
        }
        return null;
    }

    @Override
    public void deleteDocument(int id) {
        documentSinistreRepository.deleteById(id);
    }
}
