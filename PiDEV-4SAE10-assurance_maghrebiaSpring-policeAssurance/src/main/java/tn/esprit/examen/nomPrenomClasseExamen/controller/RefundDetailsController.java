package tn.esprit.examen.nomPrenomClasseExamen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;
import tn.esprit.examen.nomPrenomClasseExamen.service.IRefundDetailsService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/refunds")
public class RefundDetailsController {
  @Autowired
  private IRefundDetailsService refundDetailsService;

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
}
