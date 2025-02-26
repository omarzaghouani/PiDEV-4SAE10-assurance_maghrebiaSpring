package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FeedBackRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedBackService implements IFeedbackService {

    @Autowired
    private final FeedBackRepository feedbackRepo;

    @Override
    public FeedBack addFeedBack(FeedBack feedback) {
        // Assurer que la date de soumission est bien définie
        if (feedback.getSubmissionDate() == null) {
            feedback.setSubmissionDate(LocalDateTime.now());
        }

        // Générer l'analyse des sentiments si elle est vide
        if (feedback.getSentimentAnalysis() == null || feedback.getSentimentAnalysis().isEmpty()) {
            feedback.setSentimentAnalysis(analyzeSentiment(feedback.getDescription()));
        }

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
            existingFeedBack.setDescription(feedback.getDescription());
            existingFeedBack.setSatisfactionScore(feedback.getSatisfactionScore());
            existingFeedBack.setProductService(feedback.getProductService());
            existingFeedBack.setSentimentAnalysis(analyzeSentiment(feedback.getDescription()));
            return feedbackRepo.save(existingFeedBack);
        }).orElseThrow(() -> new IllegalArgumentException("Feedback non trouvé."));
    }

    @Override
    public void deleteFeedBack(Long id) {
        feedbackRepo.deleteById(id);
    }

    @Override
    public String analyzeSentiment(String text) {
        try {
            File scriptFile = new File("src/main/resources/python/sentiment_analysis.py");
            String scriptPath = scriptFile.getAbsolutePath();

            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath, text);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }

            process.waitFor();

            if (jsonOutput.toString().contains("Positive")) {
                return "Positive";
            } else if (jsonOutput.toString().contains("Negative")) {
                return "Negative";
            } else {
                return "Neutral";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
