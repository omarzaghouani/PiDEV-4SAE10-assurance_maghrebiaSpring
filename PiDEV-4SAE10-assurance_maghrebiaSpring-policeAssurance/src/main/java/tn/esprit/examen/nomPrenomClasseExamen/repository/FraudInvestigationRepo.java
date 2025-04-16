package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudInvestigation;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;

import java.util.List;
import java.util.Optional;

public interface FraudInvestigationRepo extends JpaRepository<FraudInvestigation, Integer> {
    List<FraudInvestigation> findByRefundDetails(RefundDetails refund);


    @Query(value = "SELECT u.email FROM fraud_investigation fi " +
            "JOIN refund_details rd ON fi.refund_id = rd.refund_id " +
            "JOIN user u ON rd.user_id = u.id " +
            "WHERE fi.fraud_case_id = :fraudCaseId", nativeQuery = true)
    String findUserEmailByFraudCaseId(@Param("fraudCaseId") int fraudCaseId);


}
