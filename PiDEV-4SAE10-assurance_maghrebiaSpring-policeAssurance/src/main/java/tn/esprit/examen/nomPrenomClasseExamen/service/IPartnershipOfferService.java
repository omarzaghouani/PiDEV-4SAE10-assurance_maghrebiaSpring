package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.PartnershipOffer;
import java.util.List;

public interface IPartnershipOfferService {
    PartnershipOffer addPartnershipOffer(PartnershipOffer p);
    PartnershipOffer updatePartnershipOffer(Long id, PartnershipOffer partnershipOfferEntity);
    void deletePartnershipOffer(Long id);
    List<PartnershipOffer> getAllPartnershipOffers();
    PartnershipOffer getPartnershipOfferById(Long id);
}
