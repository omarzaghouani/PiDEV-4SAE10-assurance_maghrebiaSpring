package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PartnershipRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PartnershipService {

    @Autowired
    private PartnershipRepository partnershipRepository;

    public List<Partnership> getAllPartnerships() {
        return partnershipRepository.findAll();
    }

    public Partnership getPartnershipById(Long id) {
        return partnershipRepository.findById(id).orElse(null);
    }

    public Partnership addPartnership(Partnership partnership) {
        return partnershipRepository.save(partnership);
    }

    public Partnership updatePartnership(Long id, Partnership updatedPartnership) {
        return partnershipRepository.findById(id)
                .map(existingPartnership -> {
                    existingPartnership.setCompanyName(updatedPartnership.getCompanyName());
                    existingPartnership.setContactEmail(updatedPartnership.getContactEmail());
                    existingPartnership.setPhoneNumber(updatedPartnership.getPhoneNumber());
                    existingPartnership.setIndustry(updatedPartnership.getIndustry());
                    existingPartnership.setAgreementDetails(updatedPartnership.getAgreementDetails());

                    // ✅ Ensure `offers` list is never null
                    if (updatedPartnership.getOffers() != null) {
                        existingPartnership.getOffers().clear();
                        existingPartnership.getOffers().addAll(updatedPartnership.getOffers());
                    }

                    return partnershipRepository.save(existingPartnership);
                })
                .orElseThrow(() -> new RuntimeException("Partnership not found"));
    }


    public void deletePartnership(Long id) {
        partnershipRepository.deleteById(id);
    }
}
