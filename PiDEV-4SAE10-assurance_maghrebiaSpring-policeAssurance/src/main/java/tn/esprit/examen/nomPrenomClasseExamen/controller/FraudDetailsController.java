package tn.esprit.examen.nomPrenomClasseExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;
import tn.esprit.examen.nomPrenomClasseExamen.service.IFraudDetailsService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/fraud-details")
public class FraudDetailsController {
  @Autowired
  private IFraudDetailsService fraudDetailsService;

  @PostMapping("/add")
  public ResponseEntity<FraudDetails> createFraudDetails(@RequestBody FraudDetails fraudDetails) {
    FraudDetails createdFraud = fraudDetailsService.addFraudDetails(fraudDetails);
    return new ResponseEntity<>(createdFraud, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FraudDetails>> getAllFraudDetails() {
    List<FraudDetails> fraudDetailsList = fraudDetailsService.getAllFraudDetails();
    return new ResponseEntity<>(fraudDetailsList, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FraudDetails> getFraudDetailsById(@PathVariable int id) {
    Optional<FraudDetails> fraudDetails = fraudDetailsService.getFraudDetailsById(id);
    return fraudDetails.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<FraudDetails> updateFraudDetails(@PathVariable int id, @RequestBody FraudDetails fraudDetails) {
    FraudDetails updatedFraud = fraudDetailsService.updateFraudDetails(id, fraudDetails);
    if (updatedFraud != null) {
      return new ResponseEntity<>(updatedFraud, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFraudDetails(@PathVariable int id) {
    fraudDetailsService.deleteFraudDetails(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @GetMapping("/fraud-case/{fraudCaseId}")
  public ResponseEntity<FraudDetails> getFraudDetailsByFraudCaseId(@PathVariable int fraudCaseId) {
    Optional<FraudDetails> fraudDetails = fraudDetailsService.getFraudDetailsByFraudCaseId(fraudCaseId);
    return fraudDetails.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
