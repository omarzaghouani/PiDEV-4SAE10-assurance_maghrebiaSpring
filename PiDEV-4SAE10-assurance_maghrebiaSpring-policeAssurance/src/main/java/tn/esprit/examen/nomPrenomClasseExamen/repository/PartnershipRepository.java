package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership; // ✅ Correct import

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
}