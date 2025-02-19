package tn.esprit.examen.nomPrenomClasseExamen.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundAudit;
import tn.esprit.examen.nomPrenomClasseExamen.repository.RefundAuditRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class RefundAuditService implements IRefundAuditService{
  @Autowired
  RefundAuditRepo refundAuditRepo;

  @Override
  public RefundAudit addRefundAudit(RefundAudit refundAudit) {
    return refundAuditRepo.save(refundAudit);
  }

  @Override
  public Optional<RefundAudit> getRefundAuditById(int id) {
    return refundAuditRepo.findById(id);
  }

  @Override
  public List<RefundAudit> getAllRefundAudits() {
    return refundAuditRepo.findAll();
  }

  @Override
  public RefundAudit updateRefundAudit(int id, RefundAudit refundAudit) {
    Optional<RefundAudit> existingAudit = refundAuditRepo.findById(id);
    if (existingAudit.isPresent()) {
      RefundAudit audit = existingAudit.get();
      audit.setProcessedBy(refundAudit.getProcessedBy());
      audit.setProcessedAt(refundAudit.getProcessedAt());
      audit.setAuditReport(refundAudit.getAuditReport());
      return refundAuditRepo.save(audit);
    }
    return null;
  }

  @Override
  public void deleteRefundAudit(int id) {
    refundAuditRepo.deleteById(id);
  }

    @Override
  public RefundAudit findByRefundId(int refundId) {
    return refundAuditRepo.findByRefundDetails_RefundId(refundId);
  }
  
}
