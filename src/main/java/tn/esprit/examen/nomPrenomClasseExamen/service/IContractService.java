package tn.esprit.examen.nomPrenomClasseExamen.service;


import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;

import java.util.List;
import java.util.Optional;

public interface IContractService {
    // Create or update contract
    Contract addContract(Contract contract);

    // Get all contracts
    List<Contract> getAllContracts();

    // Get contract by ID
    Optional<Contract> getContractById(Long id);
    Contract updateContract(Long id, Contract updatedContract);
    // Delete contract
    void deleteContract(Long id);
}
