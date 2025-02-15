package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractDocument;

import java.util.List;

public interface IContractDocumentService {
    public ContractDocument addContractDocument(ContractDocument document);
    ContractDocument getContractDocumentById(Long id);
    List<ContractDocument> getAllContractDocuments();
    ContractDocument updateContractDocument(Long id, ContractDocument contractDocument);
    void deleteContractDocument(Long id);
}
