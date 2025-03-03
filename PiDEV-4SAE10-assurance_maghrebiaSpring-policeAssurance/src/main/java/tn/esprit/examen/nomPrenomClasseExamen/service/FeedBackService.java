package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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
        if (feedback.getSubmissionDate() == null) {
            feedback.setSubmissionDate(LocalDateTime.now());
        }

        // Toujours analyser le sentiment, même si déjà fourni
        feedback.setSentimentAnalysis(analyzeSentiment(feedback.getDescription()));

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

            // Re-analyse du sentiment à partir de la nouvelle description
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
            File scriptFile = new File("PiDEV-4SAE10-assurance_maghrebiaSpring-policeAssurance/src/main/resources/python/sentiment_analysis.py");
            String scriptPath = scriptFile.getAbsolutePath();

            String safeText = text.replace("\"", "\\\"").replace("\n", " ");
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, safeText);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line.trim());
            }

            process.waitFor();

            String resultJson = jsonOutput.toString();
            if (!resultJson.startsWith("{")) {
                throw new RuntimeException("Invalid JSON returned: " + resultJson);
            }

            JSONObject result = new JSONObject(resultJson);
            System.out.println("result json**********"+resultJson.toString());
            return result.getString("sentiment");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
