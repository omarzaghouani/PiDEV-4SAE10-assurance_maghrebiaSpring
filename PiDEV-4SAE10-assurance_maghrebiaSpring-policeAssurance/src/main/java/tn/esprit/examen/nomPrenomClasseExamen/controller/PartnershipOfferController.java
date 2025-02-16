package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer;
import tn.esprit.examen.nomPrenomClasseExamen.service.IPartnershipOfferService;

import java.util.List;

@RestController
@RequestMapping("/api/partnership-offers")
@RequiredArgsConstructor

public class PartnershipOfferController {
    private final IPartnershipOfferService partnershipOfferService; // ✅ Inject service

    @PostMapping
    public ResponseEntity<PartnershipOffer> addPartnershipOffer(@RequestBody PartnershipOffer partnershipOffer) {
        PartnershipOffer savedOffer = partnershipOfferService.addPartnershipOffer(partnershipOffer);
        return ResponseEntity.ok(savedOffer);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<PartnershipOffer> updatePartnershipOffer(@PathVariable Long id, @RequestBody PartnershipOffer partnershipOffer) {
        PartnershipOffer updatedOffer = partnershipOfferService.updatePartnershipOffer(id, partnershipOffer);
        return updatedOffer != null ? ResponseEntity.ok(updatedOffer) : ResponseEntity.notFound().build();
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartnershipOffer(@PathVariable Long id) {
        partnershipOfferService.deletePartnershipOffer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PartnershipOffer>> getAllPartnershipOffers() {
        List<PartnershipOffer> partnershipOffers = partnershipOfferService.getAllPartnershipOffers();
        return ResponseEntity.ok(partnershipOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnershipOffer> getPartnershipOfferById(@PathVariable Long id) {
        PartnershipOffer partnershipOffer = partnershipOfferService.getPartnershipOfferById(id);
        return partnershipOffer != null ? ResponseEntity.ok(partnershipOffer) : ResponseEntity.notFound().build();
    }
}

