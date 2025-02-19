package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FinancialManagement;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FinancialManagementRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinancialManagementService implements IFinancialManagementService {

    private final FinancialManagementRepository financialManagementRepository;

    @Override
    public FinancialManagement addFinancialRecord(FinancialManagement financialManagement) {
        return financialManagementRepository.save(financialManagement);
    }

    @Override
    public List<FinancialManagement> getAllFinancialRecords() {
        return financialManagementRepository.findAll();
    }

    @Override
    public Optional<FinancialManagement> getFinancialRecordById(int id) {
        return financialManagementRepository.findById(id);
    }

    @Override
    public FinancialManagement updateFinancialRecord(int id, FinancialManagement updatedRecord) {
        return financialManagementRepository.findById(id).map(record -> {
            record.setTotalRevenue(updatedRecord.getTotalRevenue());
            record.setExpenses(updatedRecord.getExpenses());
            record.setNetIncome(updatedRecord.getNetIncome());
            return financialManagementRepository.save(record);
        }).orElseThrow(() -> new RuntimeException("Financial record not found with ID: " + id));
    }

    @Override
    public void deleteFinancialRecord(int id) {
        if (!financialManagementRepository.existsById(id)) {
            throw new RuntimeException("Financial record not found with ID: " + id);
        }

    }
    }
