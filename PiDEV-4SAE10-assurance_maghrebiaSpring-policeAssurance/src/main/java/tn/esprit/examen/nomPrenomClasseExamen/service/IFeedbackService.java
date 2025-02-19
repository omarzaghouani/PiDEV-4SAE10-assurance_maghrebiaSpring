package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;

import java.util.List;
import java.util.Optional;

public interface IFeedbackService  {
    FeedBack addFeedBack(FeedBack feedBack);

    List<FeedBack> getAllFeedBacks();

    Optional<FeedBack> getFeedBackById(Long id);

    FeedBack updateFeedBack(Long id, FeedBack updatedFeedBack);

    void deleteFeedBack(Long id);
}
