package tn.esprit.examen.nomPrenomClasseExamen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership;
import tn.esprit.examen.nomPrenomClasseExamen.service.IPartnershipService;
import tn.esprit.examen.nomPrenomClasseExamen.service.PartnershipService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
@RestController
@RequestMapping("/api/partnerships")
public class PartnershipController {

    @Autowired
    private PartnershipService partnershipService;

    // ✅ Get all partnerships
    @GetMapping("/all")
    public List<Partnership> getAllPartnerships() {
        return partnershipService.getAllPartnerships();
    }

    // ✅ Get partnership by ID
    @GetMapping("/{id}")
    public Partnership getPartnershipById(@PathVariable Long id) {
        return partnershipService.getPartnershipById(id);
    }

    @PostMapping("/add")
    public Partnership addPartnership(@RequestBody Partnership partnership) {
        if (partnership.getSelectedPackages() == null) {
            partnership.setSelectedPackages(new ArrayList<>()); // Ensure it's not null
        }
        return partnershipService.addPartnership(partnership);
    }

    // ✅ Update partnership (Same style as your `updatePackage` method)
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Partnership> updatePartnership(@PathVariable Long id, @RequestBody Partnership partnership) {
        Partnership updatedPartnership = partnershipService.updatePartnership(id, partnership);
        return updatedPartnership != null ? ResponseEntity.ok(updatedPartnership) : ResponseEntity.notFound().build();
    }

    // ✅ Delete partnership
    @DeleteMapping("/delete/{id}")
    public void deletePartnership(@PathVariable Long id) {
        partnershipService.deletePartnership(id);
    }
}