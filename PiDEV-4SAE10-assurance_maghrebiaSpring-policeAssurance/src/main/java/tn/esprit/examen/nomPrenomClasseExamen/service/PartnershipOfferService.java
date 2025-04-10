package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PartnershipOfferRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor // Lombok handles constructor injection
public class PartnershipOfferService implements IPartnershipOfferService {

    private final PartnershipOfferRepository partnershipOfferRepository; // ✅ Injected repository
    String currentYear = String.valueOf(LocalDate.now().getYear());

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

    //Offer Analytics Dashboard 5 Companies with Most Offers

    @Override
    public List<Object[]> getTopCompaniesWithMostOffers() {
        return partnershipOfferRepository.findTopCompaniesWithMostOffers();
    }

    @Override
    public Double getAverageDiscountRate() {
        return partnershipOfferRepository.findAverageDiscountRate();
    }

    @Override
    public Object[] getActiveVsExpiredOfferStats(String year) {
        List<PartnershipOffer> offers = partnershipOfferRepository.findAll();
        int active = 0;
        int expired = 0;

        for (PartnershipOffer offer : offers) {
            try {
                String yearString = offer.getValidityPeriod();

                if (yearString != null && !yearString.isEmpty()) {
                    int offerYear = Integer.parseInt(yearString.length() > 4 ? yearString.substring(0, 4) : yearString);

                    if (offerYear >= Integer.parseInt(currentYear)) {
                        active++;
                    } else {
                        expired++;
                    }
                }
            } catch (NumberFormatException e) {
                // Could log or ignore invalid format
                System.out.println("Invalid validityPeriod format: " + offer.getValidityPeriod());
            }
        }

        return new Object[]{active, expired};    }
}
