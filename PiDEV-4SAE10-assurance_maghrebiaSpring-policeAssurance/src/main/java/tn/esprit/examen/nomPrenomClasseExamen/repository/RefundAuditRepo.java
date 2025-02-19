package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundAudit;

public interface RefundAuditRepo extends JpaRepository<RefundAudit, Integer> {
    RefundAudit findByRefundDetails_RefundId(int refundId);
}
