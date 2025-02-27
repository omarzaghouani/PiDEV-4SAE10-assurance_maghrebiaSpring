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
    public ResponseEntity<?> addSubscription(@RequestBody Subscription subscription) {
        try {
            Subscription savedSubscription = subscriptionService.addSubscription(subscription);
            return ResponseEntity.ok(savedSubscription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding subscription: " + e.getMessage());
        }
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

    //Advanced Functions :

    @GetMapping("/exists/{subscriberName}")
    public ResponseEntity<Boolean> checkSubscriberExists(@PathVariable String subscriberName) {
        boolean exists = subscriptionService.checkIfSubscriberExists(subscriberName);
        return ResponseEntity.ok(exists);
    }

}
