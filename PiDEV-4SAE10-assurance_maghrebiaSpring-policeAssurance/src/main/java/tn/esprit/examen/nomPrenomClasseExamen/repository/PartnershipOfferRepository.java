package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer; // ✅ Correct import

import java.util.List;

@Repository
public interface PartnershipOfferRepository extends JpaRepository<PartnershipOffer, Long> {
    @Query("SELECT p.partnership.companyName AS companyName, COUNT(p) AS offerCount " +
            "FROM PartnershipOffer p GROUP BY p.partnership.companyName ORDER BY offerCount DESC")
    List<Object[]> findTopCompaniesWithMostOffers();

    @Query("SELECT AVG(p.discountRate) FROM PartnershipOffer p")
    Double findAverageDiscountRate();

    @Query("SELECT " +
            "SUM(CASE WHEN p.validityPeriod LIKE %:year% THEN 1 ELSE 0 END) AS active, " +
            "SUM(CASE WHEN p.validityPeriod NOT LIKE %:year% THEN 1 ELSE 0 END) AS expired " +
            "FROM PartnershipOffer p")
    Object[] findActiveVsExpiredCount(@Param("year") String year);
}
