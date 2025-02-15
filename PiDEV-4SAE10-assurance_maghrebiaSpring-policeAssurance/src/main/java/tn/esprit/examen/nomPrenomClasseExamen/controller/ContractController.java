package tn.esprit.examen.nomPrenomClasseExamen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.service.IContractService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    IContractService contractService;
    @PostMapping("/add")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract createdContract = contractService.addContract(contract);
        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);

    }
    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        Optional<Contract> contract = contractService.getContractById(id);
        return contract.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, @RequestBody Contract contract) {
        try {
            Contract updatedContract = contractService.updateContract(id, contract);
            return new ResponseEntity<>(updatedContract, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
