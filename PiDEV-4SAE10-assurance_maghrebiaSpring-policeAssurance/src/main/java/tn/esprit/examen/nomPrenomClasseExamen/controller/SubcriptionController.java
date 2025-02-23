package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import tn.esprit.examen.nomPrenomClasseExamen.service.ISubscriptionService;
import tn.esprit.examen.nomPrenomClasseExamen.service.SubscriptionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SubcriptionController {


    private final SubscriptionService subscriptionService;

    @PostMapping("/add")
    public ResponseEntity<Subscription> addSubscription(@RequestBody Subscription subscription) {
        Subscription savedSubscription = subscriptionService.addSubscription(subscription);
        return ResponseEntity.ok(savedSubscription);
    }

    @GetMapping("/all")  // ✅ Matches /api/subscriptions/all
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return subscription != null ? ResponseEntity.ok(subscription) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        Subscription updatedSubscription = subscriptionService.updateSubscription(id, subscription);
        return updatedSubscription != null ? ResponseEntity.ok(updatedSubscription) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().body("Subscription deleted successfully");
    }
}
