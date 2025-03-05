package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IFeedbackService {

    FeedBack addFeedBack(FeedBack feedBack);

    List<FeedBack> getAllFeedBacks();

    Optional<FeedBack> getFeedBackById(Long id);

    FeedBack updateFeedBack(Long id, FeedBack updatedFeedBack);

    void deleteFeedBack(Long id);

    String analyzeSentiment(String text);

    ByteArrayInputStream exportFeedbacksToPDF(List<FeedBack> feedbacks) throws IOException;

    ByteArrayInputStream exportFeedbacksToExcel(List<FeedBack> feedbacks);
}
