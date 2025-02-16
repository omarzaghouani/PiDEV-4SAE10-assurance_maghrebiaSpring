package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership;
import java.util.List;

public interface IPartnershipService {
    Partnership addPartnership(Partnership p);
    Partnership updatePartnership(Long id, Partnership partnershipEntity);
    void deletePartnership(Long id);
    List<Partnership> getAllPartnerships();
    Partnership getPartnershipById(Long id);
}
