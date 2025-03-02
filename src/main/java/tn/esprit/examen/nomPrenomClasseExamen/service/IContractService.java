package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatus;

import java.util.Date;
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

    // Search contracts with filters
    List<Contract> searchContracts(Date startDate, Date endDate, String status, String number);

    // Get contract file
    byte[] getContractFile(String number);

    // Count contracts by status
    long countContractsByStatus(String status);
}
