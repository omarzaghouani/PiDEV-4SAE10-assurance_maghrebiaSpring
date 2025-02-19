package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.ReclamationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationServiceImpl implements ReclamationService {

    private final ReclamationRepository reclamationRepository;

    @Autowired
    public ReclamationServiceImpl(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    @Override
    public Reclamation createReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation updateReclamation(int id, Reclamation updatedReclamation) {
        Optional<Reclamation> optionalReclamation = reclamationRepository.findById(id);

        if (optionalReclamation.isPresent()) {
            Reclamation existingReclamation = optionalReclamation.get();

            existingReclamation.setClientId(updatedReclamation.getClientId());
            existingReclamation.setTypeReclamation(updatedReclamation.getTypeReclamation());
            existingReclamation.setDescription(updatedReclamation.getDescription());
            existingReclamation.setStatutReclamation(updatedReclamation.getStatutReclamation());
            existingReclamation.setDateTraitement(updatedReclamation.getDateTraitement());

            return reclamationRepository.save(existingReclamation);
        } else {
            throw new RuntimeException("Réclamation avec l'ID " + id + " non trouvée.");
        }
    }

    @Override
    public void deleteReclamation(int id) {
        if (reclamationRepository.existsById(id)) {
            reclamationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Réclamation avec l'ID " + id + " non trouvée.");
        }
    }
}
