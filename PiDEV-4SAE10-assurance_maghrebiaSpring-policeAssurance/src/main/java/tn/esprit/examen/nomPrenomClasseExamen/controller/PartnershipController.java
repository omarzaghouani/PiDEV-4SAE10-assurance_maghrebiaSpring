package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership;
import tn.esprit.examen.nomPrenomClasseExamen.service.IPartnershipService;

import java.util.List;

@RestController
@RequestMapping("/api/partnerships")
@RequiredArgsConstructor

public class PartnershipController {
    private final IPartnershipService partnershipService;

    @PostMapping
    public ResponseEntity<Partnership> addPartnership(@RequestBody Partnership partnership) {
        Partnership savedPartnership = partnershipService.addPartnership(partnership);
        return ResponseEntity.ok(savedPartnership);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Partnership> updatePartnership(@PathVariable Long id, @RequestBody Partnership partnership) {
        Partnership updatedPartnership = partnershipService.updatePartnership(id, partnership);
        return updatedPartnership != null ? ResponseEntity.ok(updatedPartnership) : ResponseEntity.notFound().build();
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartnership(@PathVariable Long id) {
        partnershipService.deletePartnership(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Partnership>> getAllPartnerships() {
        List<Partnership> partnerships = partnershipService.getAllPartnerships();
        return ResponseEntity.ok(partnerships);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partnership> getPartnershipById(@PathVariable Long id) {
        Partnership partnership = partnershipService.getPartnershipById(id);
        return partnership != null ? ResponseEntity.ok(partnership) : ResponseEntity.notFound().build();
    }
}
