package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.*;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FraudDetailsRepo;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FraudInvestigationRepo;
import tn.esprit.examen.nomPrenomClasseExamen.repository.RefundDetailsRepo;
import tn.esprit.examen.nomPrenomClasseExamen.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RefundDetailsService  implements IRefundDetailsService {
  @Autowired
   RefundDetailsRepo refundDetailsRepo;
  
  @Autowired
  UserRepository userRepository;




  @Autowired
  private FraudInvestigationRepo fraudInvestigationRepo;

  @Autowired
  private FraudDetailsRepo fraudDetailsRepo;
  
  
  @Override
  public List<User> getAllusers() {
    return userRepository.findAll();
  }

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
    //  refund.setUser_Id(refundDetails.getUser_Id());
      refund.setAmount(refundDetails.getAmount());
      refund.setReason(refundDetails.getReason());
      refund.setProcessedAt(LocalDateTime.now());
      refund.setRefundStatus(refundDetails.getRefundStatus());
      return refundDetailsRepo.save(refund);
    }
    return null;
  }
  
  
  //  getRefundAuditByRefundId
  
  @Override
  public void deleteRefund(int id) {
    refundDetailsRepo.deleteById(id);
  }

  @Override
  public List<RefundDetails> findByUser_Id(int userId) {
    return refundDetailsRepo.findByUserId(userId);
  }



  
  @Override
  @Transactional
  public RefundDetails processRefund(RefundDetails refund) {
    // ✅ Fetch existing Fraud Investigation (if exists)
    Optional<FraudInvestigation> fraudInvestigationOpt = fraudInvestigationRepo.findByRefundDetails(refund);
    FraudInvestigation fraudInvestigation = fraudInvestigationOpt.orElse(null);

    // ✅ Get fraud-related attributes (if present in FraudInvestigation)
    int refundCount = refund.getFraudInvestigations().size(); // Number of past investigations
    System.out.println("🔍 Refund Count: " + refundCount);
    int previousFrauds = (fraudInvestigation != null) ? fraudDetailsRepo.countByFraudInvestigation(fraudInvestigation) : 0;
    System.out.println("🔍 Previous Frauds: " + previousFrauds);
    float riskScore = (fraudInvestigation != null && fraudInvestigation.getFraudDetails() != null) ?
            fraudInvestigation.getFraudDetails().getRiskScore() : 0.5f; // Default risk score
    System.out.println("🔍 Risk Score: " + riskScore);
    String actionTaken = (fraudInvestigation != null && fraudInvestigation.getFraudDetails() != null) ?
            fraudInvestigation.getFraudDetails().getActionTaken() : "None";
    System.out.println("🔍 Action Taken: " + actionTaken);

    // Call fraud detection model
    FraudDetectionService.FraudResult result = FraudDetectionService.predictFraud(
            refund.getUserId(), refund.getOrderId(), refund.getAmount(),
            mapReasonToCode(refund.getReason()), refundCount,
            previousFrauds, riskScore, actionTaken.equals("Blocked") ? 1 : 0);

    if (result.isFraud) {
      refund.setRefundStatus(RefundStatus.PENDING);
      System.out.println("🚨 Fraud Detected! Probability: " + result.probability);

      if (fraudInvestigation == null) {
        // ✅ Create a new Fraud Investigation
        fraudInvestigation = new FraudInvestigation();
        fraudInvestigation.setRefundDetails(refund);
        fraudInvestigation.setDetectedBy("AI Fraud Model");
        fraudInvestigation.setCreatedAt(LocalDateTime.now());
        fraudInvestigation.setStatus(FraudStatus.UNDER_REVIEW);
        fraudInvestigation = fraudInvestigationRepo.save(fraudInvestigation);
      }

      // ✅ Create or Update Fraud Details
      Optional<FraudDetails> existingFraudDetails = fraudDetailsRepo.findByFraudInvestigation(fraudInvestigation);
      FraudDetails fraudDetails;
      if (existingFraudDetails.isPresent()) {
        fraudDetails = existingFraudDetails.get();
      } else {
        fraudDetails = new FraudDetails();
        fraudDetails.setFraudInvestigation(fraudInvestigation);
      }
      fraudDetails.setFraudType("Automated Detection");
      fraudDetails.setRiskScore((float) result.probability);
      fraudDetails.setActionTaken("Pending Review");
      fraudDetailsRepo.save(fraudDetails);

      System.out.println("📌 Fraud Investigation Created: Case #" + fraudInvestigation.getFraudCaseId());
    } else {
      refund.setRefundStatus(RefundStatus.APPROVED);
    }

    return refundDetailsRepo.save(refund);
  }

  private int mapReasonToCode(String reason) {
    return switch (reason.toLowerCase()) {
      case "item not received" -> 1;
      case "fraudulent transaction" -> 2;
      case "wrong item" -> 3;
      case "damaged product" -> 4;
      default -> 0;
    };
  }



  @Scheduled(fixedRate = 60000) // Runs every 60 seconds
  public void checkPendingRefundsForFraud() {
    System.out.println("🔍 Running scheduled fraud detection...");
    List<RefundDetails> pendingRefunds = refundDetailsRepo.findAllByRefundStatus(RefundStatus.PENDING);

    for (RefundDetails refund : pendingRefunds) {
      // ✅ Manually fetch fraud investigations before calling processRefund()
     // refund.setFraudInvestigations(fraudInvestigationRepo.findByRefundDetails(refund));
      processRefund(refund);
    }

    System.out.println("✅ Fraud detection scheduler completed.");
  }

  
}
