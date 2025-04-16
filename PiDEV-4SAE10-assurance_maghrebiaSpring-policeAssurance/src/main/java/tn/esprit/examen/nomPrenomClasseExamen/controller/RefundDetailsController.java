package tn.esprit.examen.nomPrenomClasseExamen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.*;
import tn.esprit.examen.nomPrenomClasseExamen.repository.UserRepository;
import tn.esprit.examen.nomPrenomClasseExamen.service.IRefundDetailsService;
import tn.esprit.examen.nomPrenomClasseExamen.service.MailService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/refunds")
public class RefundDetailsController {
  @Autowired
  private IRefundDetailsService refundDetailsService;
  
  @Autowired
  private MailService mailService;
  

  @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
  public ResponseEntity<RefundDetails> createRefund(@RequestBody RefundDetails refundDetails) {
    RefundDetails createdRefund = refundDetailsService.addRefund(refundDetails);
    return new ResponseEntity<>(createdRefund, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<RefundDetails>> getAllRefunds() {
    List<RefundDetails> refunds = refundDetailsService.getAllRefunds();
    return new ResponseEntity<>(refunds, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RefundDetails> getRefundById(@PathVariable int id) {
    Optional<RefundDetails> refund = refundDetailsService.getRefundById(id);
    return refund.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<RefundDetails> updateRefund(@PathVariable int id, @RequestBody RefundDetails refundDetails) {
    RefundDetails updatedRefund = refundDetailsService.updateRefund(id, refundDetails);
    if (updatedRefund != null) {
      return new ResponseEntity<>(updatedRefund, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRefund(@PathVariable int id) {
    refundDetailsService.deleteRefund(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @GetMapping("users/all")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = refundDetailsService.getAllusers();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RefundDetails>> getRefundsByUserId(@PathVariable int userId) {
    List<RefundDetails> refunds = refundDetailsService.findByUser_Id(userId);
    return new ResponseEntity<>(refunds, HttpStatus.OK);
  }

 /* @PostMapping("/pay")
  public ResponseEntity<?> payWithPaymee(@RequestBody PaymeePayoutRequest request) {
    try {
      // 1. Send payout to Paymee
      String result = refundDetailsService.sendPaymeePayout(request);

      RefundDetails refund = refundDetailsService.getRefundById(request.getRefundId())
              .orElseThrow(() -> new RuntimeException("Refund not found"));
      refund.setRefundStatus(RefundStatus.PAID);
      refund.setProcessedAt(LocalDateTime.now());


      // 5. Generate and inject PDF audit
      byte[] pdfBytes = refundDetailsService.generateRefundPdfFromHtml(refund);

      RefundAudit audit = new RefundAudit();
      audit.setProcessedAt(LocalDateTime.now());
      audit.setProcessedBy(1); // Replace with auth context if needed
      audit.setAuditReport(pdfBytes);
      audit.setRefundDetails(refund);

      refund.setRefundAudit(audit); // link audit

      refundDetailsService.addRefund(refund);
            

      mailService.sendPaymentSuccessEmail(
              request.getRecipientEmail(),
              request.getAmount(),
              request.getNote(),
              "http://localhost:4200/back/my-refunds"
      );

      return ResponseEntity.ok(Map.of("message", "✅ Payout processed", "response", result));

    } catch (Exception e) {
      return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
  }*/

  @PostMapping("/pay")
  public ResponseEntity<?> payWithPaymee(@RequestBody PaymeePayoutRequest request) {
    try {
      System.out.println("📦 Step 1: Sending payout...");
      String result = refundDetailsService.sendPaymeePayout(request);
      System.out.println("✅ Payout sent. Result: " + result);

      System.out.println("🔍 Step 2: Fetching refund from DB...");
      RefundDetails refund = refundDetailsService.getRefundById(request.getRefundId())
              .orElseThrow(() -> new RuntimeException("Refund not found"));

      System.out.println("🛠️ Step 3: Updating refund status...");
      refund.setRefundStatus(RefundStatus.PAID);
      refund.setProcessedAt(LocalDateTime.now());

      System.out.println("🧾 Step 4: Generating PDF...");
      byte[] pdfBytes = refundDetailsService.generateRefundPdfFromHtml(refund);
      System.out.println("✅ PDF generated (" + pdfBytes.length + " bytes)");

      System.out.println("📄 Step 5: Creating refund audit...");
      RefundAudit audit = new RefundAudit();
      audit.setProcessedAt(LocalDateTime.now());
      audit.setProcessedBy(1); // Replace with actual user ID if needed
      audit.setAuditReport(pdfBytes);
      audit.setRefundDetails(refund);

      refund.setRefundAudit(audit);

      System.out.println("💾 Step 6: Saving refund with audit...");
      refundDetailsService.addRefund(refund);
      System.out.println("✅ Refund saved");

      System.out.println("📧 Step 7: Sending email...");
      mailService.sendPaymentSuccessEmail(
              request.getRecipientEmail(),
              request.getAmount(),
              request.getNote(),
              "http://localhost:4200/back/my-refunds"
      );
      System.out.println("✅ Email sent");

      return ResponseEntity.ok(Map.of("message", "✅ Payout processed", "response", result));

    } catch (Exception e) {
      e.printStackTrace(); // log full error to console
      return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
  }


}
