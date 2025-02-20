package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PartnershipRepository;

import java.util.List;

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

    public Partnership updatePartnership(Long id, Partnership partnership) {
        partnership.setId(id);
        return partnershipRepository.save(partnership);
    }

    public void deletePartnership(Long id) {
        partnershipRepository.deleteById(id);
    }
}
