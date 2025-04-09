package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.service.TwilioService;
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

    @Autowired
    private TwilioService twilioService;

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
                    partnership.setApproved(true);
                    Partnership saved = partnershipRepository.save(partnership);

                    // 📩 Send approval SMS
                    String message = "✅ Hello " + partnership.getCompanyName() + ", your partnership request has been approved!";
                    twilioService.sendSms(partnership.getPhoneNumber(), message);

                    return saved;
                })
                .orElse(null);
    }

    // ✅ Reject Partnership (Delete it)
    public boolean rejectPartnership(Long id) {
        Optional<Partnership> partnershipOpt = partnershipRepository.findById(id);
        if (partnershipOpt.isPresent()) {
            Partnership partnership = partnershipOpt.get();

            // 📩 Send rejection SMS before deletion
            String message = "❌ Hello " + partnership.getCompanyName() + ", we regret to inform you that your partnership request was rejected.";
            twilioService.sendSms(partnership.getPhoneNumber(), message);

            partnershipRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
