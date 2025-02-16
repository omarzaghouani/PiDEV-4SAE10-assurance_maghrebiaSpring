package tn.esprit.examen.nomPrenomClasseExamen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudInvestigation;
import tn.esprit.examen.nomPrenomClasseExamen.service.IFraudInvestigationService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/fraud-investigations")
public class FraudInvestigationController {
  @Autowired
  private IFraudInvestigationService fraudInvestigationService;

  @PostMapping("/add")
  public ResponseEntity<FraudInvestigation> createFraudInvestigation(@RequestBody FraudInvestigation fraudInvestigation) {
    FraudInvestigation createdInvestigation = fraudInvestigationService.addFraudInvestigation(fraudInvestigation);
    return new ResponseEntity<>(createdInvestigation, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<FraudInvestigation>> getAllFraudInvestigations() {
    List<FraudInvestigation> investigations = fraudInvestigationService.getAllFraudInvestigations();
    return new ResponseEntity<>(investigations, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FraudInvestigation> getFraudInvestigationById(@PathVariable int id) {
    Optional<FraudInvestigation> investigation = fraudInvestigationService.getFraudInvestigationById(id);
    return investigation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<FraudInvestigation> updateFraudInvestigation(@PathVariable int id, @RequestBody FraudInvestigation fraudInvestigation) {
    FraudInvestigation updatedInvestigation = fraudInvestigationService.updateFraudInvestigation(id, fraudInvestigation);
    if (updatedInvestigation != null) {
      return new ResponseEntity<>(updatedInvestigation, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFraudInvestigation(@PathVariable int id) {
    fraudInvestigationService.deleteFraudInvestigation(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
