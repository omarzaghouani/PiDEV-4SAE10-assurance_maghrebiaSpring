package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Sinistre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutSinistre;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SinistreRepository;
import java.util.List;
import java.util.Optional;

@Service
public class SinistreServiceImpl implements SinistreService {

    private final SinistreRepository sinistreRepository;

    public SinistreServiceImpl(SinistreRepository sinistreRepository) {
        this.sinistreRepository = sinistreRepository;
    }

    @Override
    public Sinistre createSinistre(Sinistre sinistre) {
        return sinistreRepository.save(sinistre);
    }

    @Override
    public List<Sinistre> getAllSinistres() {
        return sinistreRepository.findAll();
    }

    @Override
    public Sinistre getSinistreById(int id) {
        return sinistreRepository.findById(id).orElse(null);
    }

    @Override
    public Sinistre updateSinistre(int id, Sinistre sinistre) {
        Optional<Sinistre> existingSinistre = sinistreRepository.findById(id);
        if (existingSinistre.isPresent()) {
            Sinistre updatedSinistre = existingSinistre.get();
            updatedSinistre.setClientId(sinistre.getClientId());
            updatedSinistre.setTypeSinistre(sinistre.getTypeSinistre());
            updatedSinistre.setMontantEstime(sinistre.getMontantEstime());
            updatedSinistre.setStatutSinistre(sinistre.getStatutSinistre());
            updatedSinistre.setDateDeclaration(sinistre.getDateDeclaration());
            updatedSinistre.setDateValidation(sinistre.getDateValidation());
            return sinistreRepository.save(updatedSinistre);
        }
        return null;
    }

    @Override
    public void deleteSinistre(int id) {
        sinistreRepository.deleteById(id);
    }

    @Override
    public Sinistre updateStatutSinistre(int id, StatutSinistre statutSinistre) {
        Optional<Sinistre> optionalSinsitre = sinistreRepository.findById(id);

        if (optionalSinsitre.isPresent()) {
            Sinistre existingSinstre = optionalSinsitre.get();

            existingSinstre.setStatutSinistre(statutSinistre);

            return sinistreRepository.save(existingSinstre);
        } else {
            throw new RuntimeException("Sinistre avec l'ID " + id + " non trouvée.");
        }
    }
}
