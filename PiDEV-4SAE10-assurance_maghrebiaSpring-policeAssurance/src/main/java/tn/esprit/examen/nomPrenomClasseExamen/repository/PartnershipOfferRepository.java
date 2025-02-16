package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer; // ✅ Correct import

@Repository
public interface PartnershipOfferRepository extends JpaRepository<PartnershipOffer, Long> {
}
