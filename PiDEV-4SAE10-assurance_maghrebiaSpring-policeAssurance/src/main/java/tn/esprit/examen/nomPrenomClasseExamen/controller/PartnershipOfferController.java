package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer;
import tn.esprit.examen.nomPrenomClasseExamen.service.IPartnershipOfferService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PutMapping("/{id}")
    public ResponseEntity<PartnershipOffer> updatePartnershipOffer(@PathVariable Long id, @RequestBody PartnershipOffer partnershipOffer) {
        PartnershipOffer updatedOffer = partnershipOfferService.updatePartnershipOffer(id, partnershipOffer);
        return updatedOffer != null ? ResponseEntity.ok(updatedOffer) : ResponseEntity.notFound().build();
    }

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

    //Advanced Functionality

    @GetMapping("/stats/top-companies")
    public List<Map<String, Object>> getTopCompanies() {
        List<Object[]> rawData = partnershipOfferService.getTopCompaniesWithMostOffers();
        return rawData.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("companyName", row[0]);
            map.put("offerCount", row[1]);
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/stats/average-discount")
    public Double getAverageDiscount() {
        return partnershipOfferService.getAverageDiscountRate();
    }

    @GetMapping("/offer-stats")
    public ResponseEntity<Map<String, Integer>> getOfferStats() {
        String currentYear = String.valueOf(java.time.LocalDate.now().getYear());
        Object[] stats = partnershipOfferService.getActiveVsExpiredOfferStats(currentYear);

        Map<String, Integer> result = new HashMap<>();
        result.put("active", ((Number) stats[0]).intValue());
        result.put("expired", ((Number) stats[1]).intValue());

        return ResponseEntity.ok(result);
    }
}

