package tn.esprit.examen.nomPrenomClasseExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;
import tn.esprit.examen.nomPrenomClasseExamen.service.IFeedbackService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
public class FeedBackController {

    @Autowired
    private IFeedbackService iFeedbackService;

    @PostMapping("/add")
    public FeedBack addFeedBack(@RequestBody FeedBack feedBack) {
        return iFeedbackService.addFeedBack(feedBack);
    }

    @GetMapping("/all")
    public List<FeedBack> getAllFeedBacks() {
        return iFeedbackService.getAllFeedBacks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedBack> getFeedBackById(@PathVariable Long id) {
        Optional<FeedBack> feedBack = iFeedbackService.getFeedBackById(id);
        return feedBack.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<FeedBack> updateFeedBack(@PathVariable Long id, @RequestBody FeedBack updatedFeedBack) {
        try {
            FeedBack updated = iFeedbackService.updateFeedBack(id, updatedFeedBack);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable Long id) {
        iFeedbackService.deleteFeedBack(id);
        return ResponseEntity.noContent().build();
    }
}
