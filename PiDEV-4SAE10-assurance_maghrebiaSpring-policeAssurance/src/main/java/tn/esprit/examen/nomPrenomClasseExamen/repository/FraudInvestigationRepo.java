package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudInvestigation;

public interface FraudInvestigationRepo extends JpaRepository<FraudInvestigation, Integer> {
}
