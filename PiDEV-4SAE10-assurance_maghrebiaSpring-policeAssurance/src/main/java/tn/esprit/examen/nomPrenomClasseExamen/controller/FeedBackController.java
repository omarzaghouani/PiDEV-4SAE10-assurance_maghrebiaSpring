package tn.esprit.examen.nomPrenomClasseExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.service.IFeedbackService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/feedbacks")
public class FeedBackController {

    @Autowired
    private IFeedbackService iFeedbackService;

    @PostMapping("/add")
    public ResponseEntity<?> addFeedBack(@RequestBody FeedBack feedBack) {
        try {
            FeedBack newFeedback = iFeedbackService.addFeedBack(feedBack);
            return ResponseEntity.ok(newFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<FeedBack> getAllFeedBacks() {
        return iFeedbackService.getAllFeedBacks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedBack> getFeedBackById(@PathVariable("id") Long id) {
        Optional<FeedBack> feedBack = iFeedbackService.getFeedBackById(id);
        return feedBack.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeedBack> updateFeedBack(@PathVariable Long id, @RequestBody FeedBack updatedFeedBack) {
        try {
            FeedBack updated = iFeedbackService.updateFeedBack(id, updatedFeedBack);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable Long id) {
        iFeedbackService.deleteFeedBack(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sentiment-statistics")
    public ResponseEntity<Map<String, Long>> getSentimentStatistics() {
        List<FeedBack> feedbacks = iFeedbackService.getAllFeedBacks();
        Map<String, Long> sentimentStats = new HashMap<>();

        sentimentStats.put("Positive", 0L);
        sentimentStats.put("Neutral", 0L);
        sentimentStats.put("Negative", 0L);

        for (FeedBack feedback : feedbacks) {
            sentimentStats.put(feedback.getSentimentAnalysis(),
                    sentimentStats.getOrDefault(feedback.getSentimentAnalysis(), 0L) + 1);
        }

        return ResponseEntity.ok(sentimentStats);
    }
}
