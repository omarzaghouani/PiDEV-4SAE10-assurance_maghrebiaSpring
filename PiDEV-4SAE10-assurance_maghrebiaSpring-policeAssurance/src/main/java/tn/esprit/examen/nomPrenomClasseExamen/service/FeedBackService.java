package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FeedBackRepository;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class FeedBackService implements IFeedbackService {
    @Autowired
    private FeedBackRepository feedBackRepository;

    // Ajouter un feedback
    public FeedBack addFeedBack(FeedBack feedBack) {
        return feedBackRepository.save(feedBack);
    }

    // Récupérer tous les feedbacks
    public List<FeedBack> getAllFeedBacks() {
        return feedBackRepository.findAll();
    }

    // Récupérer un feedback par ID
    public Optional<FeedBack> getFeedBackById(Long id) {
        return feedBackRepository.findById(id);
    }

    // Mettre à jour un feedback
    public FeedBack updateFeedBack(Long id, FeedBack updatedFeedBack) {
        return feedBackRepository.findById(id).map(feedBack -> {
            feedBack.setMessage(updatedFeedBack.getMessage());
            return feedBackRepository.save(feedBack);
        }).orElseThrow(() -> new RuntimeException("FeedBack not found"));
    }

    // Supprimer un feedback
    public void deleteFeedBack(Long id) {
        feedBackRepository.deleteById(id);
    }
}
