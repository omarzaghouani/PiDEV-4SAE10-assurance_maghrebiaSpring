package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundStatus;

import java.util.List;

public interface RefundDetailsRepo extends JpaRepository<RefundDetails, Integer> {
    List<RefundDetails> findByUserId(int userId);


    @Query("SELECT r FROM RefundDetails r LEFT JOIN FETCH r.fraudInvestigations WHERE r.refundStatus = 'PENDING'")
    List<RefundDetails> findAllByRefundStatus(RefundStatus refundStatus);
}
