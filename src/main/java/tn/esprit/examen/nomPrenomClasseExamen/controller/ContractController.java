package tn.esprit.examen.nomPrenomClasseExamen.controller;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatistics;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatus;
import tn.esprit.examen.nomPrenomClasseExamen.service.IContractService;
import tn.esprit.examen.nomPrenomClasseExamen.service.IEmailService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/contrats")
public class ContractController {
    private static final Logger log = LoggerFactory.getLogger(ContractController.class);
    @Autowired
    private IContractService contractService;

    @Autowired
    private IEmailService emailService;


    @PostMapping("/add")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        if (contract == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le contrat ne peut pas être null");
        }

        Contract createdContract = contractService.addContract(contract);

        if (createdContract.getClientEmail() != null && !createdContract.getClientEmail().isEmpty()) {
            try {
                emailService.sendConfirmationEmail(
                        createdContract.getClientEmail(),
                        "Confirmation de votre contrat",
                        "Bonjour,\n\nVotre contrat a été créé avec succès. Numéro de contrat : " + createdContract.getNumber() +
                                "\n\nMerci de nous faire confiance."
                );
            } catch (MessagingException e) {
                log.error("Échec de l'envoi de l'e-mail pour le contrat {}", createdContract.getNumber(), e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'envoi de l'e-mail");
            }
        } else {
            log.warn("Aucun e-mail trouvé pour le contrat {}", createdContract.getNumber());
        }

        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContrats() {
        List<Contract> contrats = contractService.getAllContracts();
        return ResponseEntity.ok().body(contrats);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Contract>> searchContracts(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String number) {

        List<Contract> contracts = contractService.searchContracts(startDate, endDate, status, number);
        return contracts.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("/download/{number}")
    public ResponseEntity<ByteArrayResource> downloadContract(@PathVariable String number) {
        byte[] fileContent = contractService.getContractFile(number);

        if (fileContent.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ByteArrayResource resource = new ByteArrayResource(fileContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrat_" + number + ".pdf")
                .body(resource);
    }

    @GetMapping("/count/{status}")
    public long getContractCountByStatus(@PathVariable String status) {
        return contractService.countContractsByStatus(status);
    }

    @GetMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        try {
            emailService.sendConfirmationEmail("omarzaghouani01@gmail.com", "Test", "Ceci est un test.");
            return ResponseEntity.ok("E-mail envoyé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}










