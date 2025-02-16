package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Partnership;
import tn.esprit.examen.nomPrenomClasseExamen.repository.PartnershipRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok handles constructor injection
public class PartnershipService implements IPartnershipService {

    private final PartnershipRepository partnershipRepository;

    @Override
    public Partnership addPartnership(Partnership p) {
        return partnershipRepository.save(p);
    }

    @Override
    public Partnership updatePartnership(Long id, Partnership partnershipEntity) {
        /*
        return partnershipRepository.findById(id)
                .map(existingPartnership -> {
                    existingPartnership.setName(partnershipEntity.getName());
                    existingPartnership.setDescription(partnershipEntity.getDescription());
                    existingPartnership.setPartnerType(partnershipEntity.getPartnerType());
                    return partnershipRepository.save(existingPartnership);
                })
                .orElse(null);

         */
        return null;
    }

    @Override
    public void deletePartnership(Long id) {
        partnershipRepository.deleteById(id);
    }

    @Override
    public List<Partnership> getAllPartnerships() {
        return partnershipRepository.findAll();
    }

    @Override
    public Partnership getPartnershipById(Long id) {
        return partnershipRepository.findById(id).orElse(null);
    }
}
