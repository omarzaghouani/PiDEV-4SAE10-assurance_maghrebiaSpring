package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {
}
