package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.DocumentSinistre;

import java.util.List;

public interface DocumentSinistreService {
    DocumentSinistre createDocument(DocumentSinistre documentSinistre);
    List<DocumentSinistre> getAllDocuments();
    DocumentSinistre getDocumentById(int id);
    DocumentSinistre updateDocument(int id, DocumentSinistre documentSinistre);
    void deleteDocument(int id);
}
