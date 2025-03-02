package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatistics;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatus;
import tn.esprit.examen.nomPrenomClasseExamen.repository.ContractRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@AllArgsConstructor
@Service
public class ContractService implements IContractService {
    private final ContractRepository contractRepository;

    private static final Path CONTRACTS_DIRECTORY = Paths.get("C:\\Users\\omarz\\Dropbox\\PC\\Downloads");



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
        }).orElseThrow(() -> new RuntimeException("Contract not found with ID: " + id));
    }

    @Override
    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public List<Contract> searchContracts(Date startDate, Date endDate, String status, String number) {
        return contractRepository.findByAdvancedSearch(startDate, endDate, status, number);
    }

    @Override
    public byte[] getContractFile(String number) {
        try {
            // Récupérer tous les fichiers PDF dans le répertoire
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(CONTRACTS_DIRECTORY, "*.pdf");


            Path tempZipPath = Files.createTempFile("contracts_", ".zip");
            try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(tempZipPath))) {
                for (Path filePath : directoryStream) {
                    // Ajouter chaque fichier PDF dans le zip
                    try (FileInputStream fileInputStream = new FileInputStream(filePath.toFile())) {
                        ZipEntry zipEntry = new ZipEntry(filePath.getFileName().toString());
                        zipOut.putNextEntry(zipEntry);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fileInputStream.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, length);
                        }
                        zipOut.closeEntry();
                    }
                }
            }

            // Retourner le fichier zip créé sous forme de tableau de bytes
            return Files.readAllBytes(tempZipPath);
        } catch (IOException e) {
            System.err.println("Error reading contract files: " + e.getMessage());
            return new byte[0]; // Retourner un tableau vide en cas d'erreur
        }
    }


@Override
    public long countContractsByStatus(String status) {
        try {
            ContractStatus contractStatus = ContractStatus.valueOf(status.toUpperCase()); // Convertir String -> Enum
            return contractRepository.countByStatus(contractStatus);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid contract status: " + status);
        }

    }


}



