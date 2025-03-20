package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PartnershipOfferRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok handles constructor injection
public class PartnershipOfferService implements IPartnershipOfferService {

    private final PartnershipOfferRepository partnershipOfferRepository; // ✅ Injected repository

    @Override
    public PartnershipOffer addPartnershipOffer(PartnershipOffer p) {
        return partnershipOfferRepository.save(p);
    }

    @Override
    public PartnershipOffer updatePartnershipOffer(Long id, PartnershipOffer partnershipOfferEntity) {
        return partnershipOfferRepository.findById(id)
                .map(existingOffer -> {
                    existingOffer.setOfferName(partnershipOfferEntity.getOfferName());
                    existingOffer.setOfferDetails(partnershipOfferEntity.getOfferDetails());
                    existingOffer.setDiscountRate(partnershipOfferEntity.getDiscountRate());
                    existingOffer.setValidityPeriod(partnershipOfferEntity.getValidityPeriod());
                    existingOffer.setPartnership(partnershipOfferEntity.getPartnership());
                    existingOffer.setAPackage(partnershipOfferEntity.getAPackage());
                    return partnershipOfferRepository.save(existingOffer);
                })
                .orElse(null);
    }


    @Override
    public void deletePartnershipOffer(Long id) {
        partnershipOfferRepository.deleteById(id);
    }

    @Override
    public List<PartnershipOffer> getAllPartnershipOffers() {
        return partnershipOfferRepository.findAll();
    }

    @Override
    public PartnershipOffer getPartnershipOfferById(Long id) {
        return partnershipOfferRepository.findById(id).orElse(null);
    }
}
