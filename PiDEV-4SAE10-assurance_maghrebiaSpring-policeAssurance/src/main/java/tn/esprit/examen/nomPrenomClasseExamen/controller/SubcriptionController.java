package tn.esprit.examen.nomPrenomClasseExamen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import tn.esprit.examen.nomPrenomClasseExamen.service.ISubscriptionService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")  // ✅ Ensure frontend can access API
@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor

public class SubcriptionController {
    private final ISubscriptionService subscriptionService;

    @PostMapping("/add")
    public ResponseEntity<?> addSubscription(@RequestBody Subscription subscription) {
        try {
            if (subscription.getAPackage() == null || subscription.getAPackage().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("❌ Error: Package ID must not be null.");
            }
            Subscription savedSubscription = subscriptionService.addSubscription(subscription);
            return ResponseEntity.ok(savedSubscription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error adding subscription: " + e.getMessage());
        }
    }


    // ✅ Update Subscription
    @PutMapping("/update/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        Subscription updatedSubscription = subscriptionService.updateSubscription(id, subscription);
        return updatedSubscription != null ? ResponseEntity.ok(updatedSubscription) : ResponseEntity.notFound().build();
    }

    // ✅ Delete Subscription
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get All Subscriptions
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    // ✅ Get Subscription By ID
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return subscription != null ? ResponseEntity.ok(subscription) : ResponseEntity.notFound().build();
    }
}
