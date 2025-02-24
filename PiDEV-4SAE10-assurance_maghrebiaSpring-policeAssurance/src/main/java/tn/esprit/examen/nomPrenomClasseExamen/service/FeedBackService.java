package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FeedBackRepository;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedBackService implements IFeedbackService {

    @Autowired
    private final FeedBackRepository feedbackRepo;

    @Override
    public FeedBack addFeedBack(FeedBack feedback) {
        return feedbackRepo.save(feedback);
    }

    @Override
    public Optional<FeedBack> getFeedBackById(Long id) {
        return feedbackRepo.findById(id);
    }

    @Override
    public List<FeedBack> getAllFeedBacks() {
        return feedbackRepo.findAll();
    }

    @Override
    public FeedBack updateFeedBack(Long id, FeedBack feedback) {
        return feedbackRepo.findById(id).map(existingFeedBack -> {
            existingFeedBack.setUser(feedback.getUser());
            existingFeedBack.setSubmissionDate(feedback.getSubmissionDate());
            existingFeedBack.setDescription(feedback.getDescription());
            existingFeedBack.setSatisfactionScore(feedback.getSatisfactionScore());
            existingFeedBack.setProductService(feedback.getProductService());
            existingFeedBack.setSentimentAnalysis(feedback.getSentimentAnalysis());
            return feedbackRepo.save(existingFeedBack);
        }).orElse(null);
    }

    @Override
    public void deleteFeedBack(Long id) {
        feedbackRepo.deleteById(id);
    }
}
