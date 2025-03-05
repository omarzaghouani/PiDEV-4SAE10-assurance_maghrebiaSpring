package tn.esprit.examen.nomPrenomClasseExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FeedBackRepository;
import tn.esprit.examen.nomPrenomClasseExamen.service.FeedBackService;
import tn.esprit.examen.nomPrenomClasseExamen.service.IFeedbackService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/feedbacks")
public class FeedBackController {


    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private FeedBackService feedBackService;


    @PostMapping("/add")
    public FeedBack addFeedBack(@RequestBody FeedBack feedBack) {
        return feedBackService.addFeedBack(feedBack);
    }

    @GetMapping("/all")
    public List<FeedBack> getAllFeedBacks() {
        return feedBackService.getAllFeedBacks();
    }

    @GetMapping("/iooo/{id}") // Change the endpoint to just use the ID
    public ResponseEntity<FeedBack> getFeedBackById(@PathVariable("id") Long id) {
        Optional<FeedBack> feedBack = feedBackService.getFeedBackById(id);
        return feedBack.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/Update/{id}")
    public ResponseEntity<FeedBack> updateFeedBack(@PathVariable Long id, @RequestBody FeedBack updatedFeedBack) {
        try {
            FeedBack updated = feedBackService.updateFeedBack(id, updatedFeedBack);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable Long id) {
        feedBackService.deleteFeedBack(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/sentiment-statistics")
    public ResponseEntity<Map<String, Long>> getSentimentStatistics() {
        List<FeedBack> feedbacks = feedBackService.getAllFeedBacks();
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
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPDF() throws IOException {
        List<FeedBack> feedbacks = feedBackRepository.findAll();
        ByteArrayInputStream bis = feedBackService.exportFeedbacksToPDF(feedbacks);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=feedbacks.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() {
        List<FeedBack> feedbacks = feedBackRepository.findAll();
        ByteArrayInputStream bis = feedBackService.exportFeedbacksToExcel(feedbacks);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=feedbacks.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bis.readAllBytes());
    }



}
