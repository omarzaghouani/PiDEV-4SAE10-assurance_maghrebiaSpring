package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FinancialManagement;

import java.util.List;
import java.util.Optional;

public interface IFinancialManagementService {
    FinancialManagement addFinancialRecord(FinancialManagement financialManagement);
    List<FinancialManagement> getAllFinancialRecords();
    Optional<FinancialManagement> getFinancialRecordById(int id);
    FinancialManagement updateFinancialRecord(int id, FinancialManagement updatedRecord);
    void deleteFinancialRecord(int id);
}
