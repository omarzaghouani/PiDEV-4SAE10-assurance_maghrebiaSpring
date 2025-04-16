package tn.esprit.examen.nomPrenomClasseExamen.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RefundDetailsService  implements IRefundDetailsService {
  @Autowired
   RefundDetailsRepo refundDetailsRepo;
  
  @Autowired
  UserRepository userRepository;

  @Autowired
  MailService mailService;



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



  
/*  @Override
  @Transactional
  public RefundDetails processRefund(RefundDetails refund) {
    System.out.println("🔍 Processing refund for fraud detection: " + refund.getRefundId());

    // ✅ Fetch all fraud investigations related to this refund
    List<FraudInvestigation> fraudInvestigations = fraudInvestigationRepo.findByRefundDetails(refund);

    if (fraudInvestigations.isEmpty()) {
      System.out.println("⚠️ No fraud investigation found, creating a new one...");

      // ✅ Create a new Fraud Investigation if none exist
      FraudInvestigation fraudInvestigation = new FraudInvestigation();
      fraudInvestigation.setRefundDetails(refund);
      fraudInvestigation.setDetectedBy("AI Fraud Model");
      fraudInvestigation.setCreatedAt(LocalDateTime.now());
      fraudInvestigation.setStatus(FraudStatus.UNDER_REVIEW);
      fraudInvestigation = fraudInvestigationRepo.save(fraudInvestigation);
      fraudInvestigations.add(fraudInvestigation);
      System.out.println("✅ Fraud Investigation Created: " + fraudInvestigation.getFraudCaseId());
    } else {
      System.out.println("🔎 Multiple fraud investigations exist for this refund.");
    }

    for (FraudInvestigation fraudInvestigation : fraudInvestigations) {
      // ✅ Ensure fraud details exist for each fraud investigation
      Optional<FraudDetails> existingFraudDetails = fraudDetailsRepo.findByFraudInvestigation(fraudInvestigation);
      FraudDetails fraudDetails = existingFraudDetails.orElse(new FraudDetails());
      fraudDetails.setFraudInvestigation(fraudInvestigation);
      fraudDetails.setFraudType("Automated Detection");
      fraudDetails.setRiskScore(0.9f);
      fraudDetails.setActionTaken("Pending Review");
      fraudDetailsRepo.save(fraudDetails);
      System.out.println("✅ Fraud Details Updated for Case #" + fraudInvestigation.getFraudCaseId());
    }

    // ✅ Update refund status
    refund.setRefundStatus(RefundStatus.PROCESSED);
    return refundDetailsRepo.save(refund);
  }*/
@Override
@Transactional
public RefundDetails processRefund(RefundDetails refund) {

  String userEmail =   userRepository.findEmailById(refund.getUserId()); 
  String link = "http://localhost:4200/back/my-refunds";
  
  System.out.println("🔍 Processing refund for fraud detection: " + refund.getRefundId());

  // ✅ Count previous fraud investigations for the user
  int refundCount = refundDetailsRepo.countByUserId(refund.getUserId()); // Number of refunds by this user
  int previousFrauds = fraudDetailsRepo.countByUserId(refund.getUserId()); // Number of past fraud cases

  // ✅ Call ML Model for fraud prediction
  FraudDetectionService.FraudResult result = FraudDetectionService.predictFraud(
          refund.getUserId(), refund.getOrderId(), refund.getAmount(),
          mapReasonToCode(refund.getReason()), refundCount,
          previousFrauds// Default risk score
           // Default actionTaken (not blocked)
  );

  System.out.println("🚨 ML Prediction: Fraud=" + result.isFraud + ", Probability=" + result.probability);

  if (result.isFraud) {
    refund.setRefundStatus(RefundStatus.PROCESSED); // Mark refund as suspicious

    // ✅ Check if a fraud investigation already exists
    List<FraudInvestigation> fraudInvestigations = fraudInvestigationRepo.findByRefundDetails(refund);

    if (fraudInvestigations.isEmpty()) {
      System.out.println("⚠️ No fraud investigation found, creating a new one...");

      // ✅ Create a new fraud investigation
      FraudInvestigation fraudInvestigation = new FraudInvestigation();
      fraudInvestigation.setRefundDetails(refund);
      fraudInvestigation.setDetectedBy("AI Fraud Model");
      fraudInvestigation.setCreatedAt(LocalDateTime.now());
      fraudInvestigation.setStatus(FraudStatus.UNDER_REVIEW);
      fraudInvestigation = fraudInvestigationRepo.save(fraudInvestigation);

      // ✅ Create a new FraudDetails record
      FraudDetails fraudDetails = new FraudDetails();
      fraudDetails.setFraudInvestigation(fraudInvestigation);
      fraudDetails.setFraudType("Automated Detection");
      fraudDetails.setRiskScore((float) result.probability); // ✅ Use ML risk score
      fraudDetails.setActionTaken("Pending Review");
      fraudDetailsRepo.save(fraudDetails);

      System.out.println("✅ Fraud Investigation Created: Case #" + fraudInvestigation.getFraudCaseId());
      /*mailService.sendHtmlEmail(
              userEmail,
              " 🔍Refund in process",
              "Good news! Your refund is under study.",
              "View My Refunds",
              link
      );*/
    }
  } else {
    // ✅ If not fraud, approve the refund
    refund.setRefundStatus(RefundStatus.APPROVED);
    System.out.println("✅ Refund approved (No fraud detected).");
  /**//*  mailService.sendHtmlEmail(
            userEmail,
            "✅ Refund Approved",
            "Good news! Your refund has been approved.",
            "View My Refunds",
            link
    );*/
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



  @Scheduled(fixedRate = 20000) // Runs every 20 seconds
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


  public String sendPaymeePayout(PaymeePayoutRequest request) throws Exception {
    String apiUrl = "https://sandbox.paymee.tn/api/v1/payouts";
    String apiKey = "7fd01a99bcb296b8e79be6d3ddd39b6ca260c434"; // 🔐 Use your key

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Token " + apiKey);

    Map<String, Object> payload = new HashMap<>();
    payload.put("recipient", request.getRecipientEmail());
    payload.put("amount", request.getAmount());
    payload.put("note", request.getNote());

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

    return response.getBody();
  }

  public byte[] generateRefundPdfFromHtml(RefundDetails refund) throws IOException {
    String logoPath = Objects.requireNonNull(getClass().getResource("/image/OIP.jpg"), "Logo image not found").toExternalForm();

    String html = "<html><head>"
            + "<style> body { font-family: Arial, sans-serif; padding: 20px; } </style>"
            + "</head><body>"
            + "<div style='text-align: center;'>"
            + "<img src='" + logoPath + "' style='width: 200px; margin-bottom: 20px;'/>"
            + "<h2 style='color: #28a745;'>Remboursement Confirmé</h2>"
            + "</div>"
            + "<p>Ce document confirme que le remboursement a été traité :</p>"
            + "<ul>"
            + "<li><strong>ID :</strong> " + refund.getRefundId() + "</li>"
            + "<li><strong>Montant :</strong> " + refund.getAmount() + " DT</li>"
            + "<li><strong>Date :</strong> " + new Date() + "</li>"
            + "<li><strong>Status :</strong> " + refund.getRefundStatus() + "</li>"
            + "</ul>"
            + "<p>Merci d'avoir utilisé notre service.</p>"
            + "</body></html>";

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfRendererBuilder builder = new PdfRendererBuilder();
    builder.useFastMode();
    builder.withHtmlContent(html, null);
    builder.toStream(baos);
    builder.run();
    return baos.toByteArray();
  }


}
