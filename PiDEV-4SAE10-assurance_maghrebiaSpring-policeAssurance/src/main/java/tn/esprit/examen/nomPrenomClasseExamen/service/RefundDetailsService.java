package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;
import tn.esprit.examen.nomPrenomClasseExamen.repository.RefundDetailsRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RefundDetailsService  implements IRefundDetailsService {
  @Autowired
   RefundDetailsRepo refundDetailsRepo;

  @Override
  public RefundDetails addRefund(RefundDetails refundDetails) {
    return refundDetailsRepo.save(refundDetails);
  }

  @Override
  public Optional<RefundDetails> getRefundById(int id) {
    return refundDetailsRepo.findById(id);
  }

  @Override
  public List<RefundDetails> getAllRefunds() {
    return refundDetailsRepo.findAll();
  }

  @Override
  public RefundDetails updateRefund(int id, RefundDetails refundDetails) {
    Optional<RefundDetails> existingRefund = refundDetailsRepo.findById(id);
    if (existingRefund.isPresent()) {
      RefundDetails refund = existingRefund.get();
      refund.setOrderId(refundDetails.getOrderId());
 //     refund.setUser_Id(refundDetails.getUser_Id());
      refund.setAmount(refundDetails.getAmount());
      refund.setReason(refundDetails.getReason());
      refund.setRefundStatus(refundDetails.getRefundStatus());
      return refundDetailsRepo.save(refund);
    }
    return null;
  }

  @Override
  public void deleteRefund(int id) {
    refundDetailsRepo.deleteById(id);
  }
}
