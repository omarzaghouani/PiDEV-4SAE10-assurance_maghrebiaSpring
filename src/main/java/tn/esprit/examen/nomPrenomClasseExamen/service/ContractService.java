package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.repository.ContractRepository;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class ContractService<T> implements IContractService {
     ContractRepository contractRepository;

    @Override
    public Contract addContract(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    @Override
    public Contract updateContract(Long id, Contract updatedContract) {
        return contractRepository.findById(id).map(contract -> {
            contract.setNumber(updatedContract.getNumber());
            contract.setStartDate(updatedContract.getStartDate());
            contract.setEndDate(updatedContract.getEndDate());
            contract.setStatus(updatedContract.getStatus());
            return contractRepository.save(contract);
        }).orElseThrow(() -> new RuntimeException("Contrat non trouvé avec ID: " + id));
    }

    @Override
    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
}

