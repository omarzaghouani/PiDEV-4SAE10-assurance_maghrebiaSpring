package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.email.EmailService;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Reclamation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutReclamation;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.ReclamationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationServiceImpl implements ReclamationService {

    @Autowired
    private  ReclamationRepository reclamationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public ReclamationServiceImpl(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    @Override
    public Reclamation createReclamation(Reclamation reclamation) {
        Reclamation savedReclamation = reclamationRepository.save(reclamation);

        String clientEmail = "khdhia29@gmail.com";
        String subject = "New Reclamation Created";
        String body = "Dear Client,\n\n"
                + "Thank you for reaching out to us. A new reclamation has been created with the following details:\n\n"
                + "Reclamation ID: " + savedReclamation.getId() + "\n"
                + "Type: " + savedReclamation.getTypeReclamation() + "\n"
                + "Description: " + savedReclamation.getDescription() + "\n\n"
                + "We will treat your reclamation as soon as possible and keep you updated on the progress.\n\n"
                + "Thank you for your patience and understanding.\n\n"
                + "Best regards,\n"
                + "Your Support Team";

        emailService.sendEmail(clientEmail, subject, body);

        return savedReclamation;
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
    public Reclamation updateStatutReclamation(int id, StatutReclamation statutReclamation) {
        Optional<Reclamation> optionalReclamation = reclamationRepository.findById(id);

        if (optionalReclamation.isPresent()) {
            Reclamation existingReclamation = optionalReclamation.get();

            existingReclamation.setStatutReclamation(statutReclamation);

            return reclamationRepository.save(existingReclamation);
        } else {
            throw new RuntimeException("Réclamation avec l'ID " + id + " non trouvée.");
        }
    }

    @Override
    public Reclamation getRecById(int id) {
        return reclamationRepository.findById(id).orElse(null);
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
