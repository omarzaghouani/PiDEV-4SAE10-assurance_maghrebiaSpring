package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractDocument;

import tn.esprit.examen.nomPrenomClasseExamen.repository.ContractDocumentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContractDocumentService implements IContractDocumentService {
    @Autowired
     ContractDocumentRepository contractDocumentRepository;


    @Override
    public ContractDocument addContractDocument(ContractDocument document) {
        return contractDocumentRepository.save(document);
    }

    @Override
    public ContractDocument getContractDocumentById(Long id) {
        Optional<ContractDocument> contractDoc = contractDocumentRepository.findById(id);
        return contractDoc.orElse(null);
    }

    @Override
    public List<ContractDocument> getAllContractDocuments() {
        return contractDocumentRepository.findAll();
    }

    @Override
    public ContractDocument updateContractDocument(Long id, ContractDocument contractDocument) {
        Optional<ContractDocument> existingDoc = contractDocumentRepository.findById(id);
        if (existingDoc.isPresent()) {
            ContractDocument doc = existingDoc.get();
            doc.setFileName(contractDocument.getFileName());
            doc.setPath(contractDocument.getPath());
            doc.setType(contractDocument.getType());
            return contractDocumentRepository.save(doc);
        }
        return null; // Ou lancer une exception
    }


    @Override
    public void deleteContractDocument(Long id) {
        contractDocumentRepository.deleteById(id);
    }
}
