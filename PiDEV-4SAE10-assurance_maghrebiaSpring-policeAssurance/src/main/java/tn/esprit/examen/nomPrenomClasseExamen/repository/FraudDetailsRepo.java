package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractDocument;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;

public interface FraudDetailsRepo extends JpaRepository<FraudDetails, Integer> {
}
