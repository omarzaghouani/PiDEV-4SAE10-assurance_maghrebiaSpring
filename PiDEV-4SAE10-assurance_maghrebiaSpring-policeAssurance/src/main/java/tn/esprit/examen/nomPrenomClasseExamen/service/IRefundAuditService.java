package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundAudit;

import java.util.List;
import java.util.Optional;

public interface IRefundAuditService {
  RefundAudit addRefundAudit(RefundAudit refundAudit);

  Optional<RefundAudit> getRefundAuditById(int id);

  List<RefundAudit> getAllRefundAudits();

  RefundAudit updateRefundAudit(int id, RefundAudit refundAudit);

  void deleteRefundAudit(int id);


  RefundAudit findByRefundId(int refundId);
}
