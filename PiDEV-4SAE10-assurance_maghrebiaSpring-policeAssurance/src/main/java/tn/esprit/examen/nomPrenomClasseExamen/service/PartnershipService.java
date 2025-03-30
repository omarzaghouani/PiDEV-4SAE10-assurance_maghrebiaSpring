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
                    existingPartnership.setApproved(updatedPartnership.isApproved()); // ✅ Ensure approval status updates

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
// Advanced Fuctions

    // ✅ Approve Partnership
    public Partnership approvePartnership(Long id) {
        return partnershipRepository.findById(id)
                .map(partnership -> {
                    partnership.setApproved(true); // ✅ Mark as approved
                    return partnershipRepository.save(partnership);
                })
                .orElse(null);
    }

    // ✅ Reject Partnership (Delete it)
    public boolean rejectPartnership(Long id) {
        Optional<Partnership> partnership = partnershipRepository.findById(id);
        if (partnership.isPresent()) {
            partnershipRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
