package tn.esprit.examen.nomPrenomClasseExamen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundAudit;
import tn.esprit.examen.nomPrenomClasseExamen.service.IRefundAuditService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/refund-audits")
public class RefundAuditController {
  @Autowired
  private IRefundAuditService refundAuditService;

  @PostMapping("/add")
  public ResponseEntity<RefundAudit> createRefundAudit(@RequestBody RefundAudit refundAudit) {
    RefundAudit createdAudit = refundAuditService.addRefundAudit(refundAudit);
    return new ResponseEntity<>(createdAudit, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<RefundAudit>> getAllRefundAudits() {
    List<RefundAudit> audits = refundAuditService.getAllRefundAudits();
    return new ResponseEntity<>(audits, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RefundAudit> getRefundAuditById(@PathVariable int id) {
    Optional<RefundAudit> audit = refundAuditService.getRefundAuditById(id);
    return audit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<RefundAudit> updateRefundAudit(@PathVariable int id, @RequestBody RefundAudit refundAudit) {
    RefundAudit updatedAudit = refundAuditService.updateRefundAudit(id, refundAudit);
    if (updatedAudit != null) {
      return new ResponseEntity<>(updatedAudit, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRefundAudit(@PathVariable int id) {
    refundAuditService.deleteRefundAudit(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/refund/{refundId}")
  public ResponseEntity<RefundAudit> getRefundAuditByRefundId(@PathVariable int refundId) {
    RefundAudit audit = refundAuditService.findByRefundId(refundId);
    if (audit != null) {
      return ResponseEntity.ok(audit);
    } else {
      return ResponseEntity.notFound().build();
    }
  }



  @GetMapping("/download/{id}")
  public ResponseEntity<byte[]> downloadAuditReport(@PathVariable int id) {
    Optional<RefundAudit> auditOptional = refundAuditService.getRefundAuditById(id);
    if (auditOptional.isPresent()) {
      RefundAudit audit = auditOptional.get();
      byte[] auditReport = audit.getAuditReport();

      return ResponseEntity.ok()
              .header("Content-Disposition", "attachment; filename=audit-report-" + id + ".pdf") // Adjust filename/extension
              .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
              .body(auditReport);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  @GetMapping("/view/{id}")
  public ResponseEntity<byte[]> viewAuditReport(@PathVariable int id) {
    Optional<RefundAudit> auditOptional = refundAuditService.getRefundAuditById(id);
    if (auditOptional.isPresent()) {
      RefundAudit audit = auditOptional.get();
      byte[] auditReport = audit.getAuditReport();

      return ResponseEntity.ok()
              .contentType(org.springframework.http.MediaType.APPLICATION_PDF) // Adjust for your file type
              .body(auditReport);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


}
