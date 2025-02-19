package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FinancialManagement;

public interface FinancialManagementRepository extends JpaRepository<FinancialManagement, Integer> {
}
