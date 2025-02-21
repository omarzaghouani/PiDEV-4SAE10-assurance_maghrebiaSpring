package tn.esprit.examen.nomPrenomClasseExamen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    // ✅ GET ALL SUBSCRIPTIONS
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    // ✅ GET SUBSCRIPTION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return subscription != null ? ResponseEntity.ok(subscription) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Subscription> addSubscription(@RequestBody Subscription subscription) {
        try {
            System.out.println("Received subscription: " + subscription);
            if (subscription.getAPackage() == null) {
                System.out.println("Package is null, returning bad request.");
                return ResponseEntity.badRequest().body(null);  // Prevent NULL package issues
            }
            Subscription savedSubscription = subscriptionService.addSubscription(subscription);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscription); // 201 CREATED
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody Subscription updatedSubscription) {
        try {
            System.out.println("Updating subscription with ID: " + id);
            System.out.println("Received subscription details: " + updatedSubscription);
            if (updatedSubscription.getAPackage() == null) {
                System.out.println("Package is null, returning bad request.");
                return ResponseEntity.badRequest().body(null);  // Prevent NULL package issues
            }
            Subscription subscription = subscriptionService.updateSubscription(id, updatedSubscription);
            if (subscription == null) {
                System.out.println("Subscription not found, returning not found.");
                return ResponseEntity.notFound().build();
            }
            System.out.println("Subscription updated successfully.");
            return ResponseEntity.ok(subscription);
        } catch (Exception e) {
            // Log the exception
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ✅ DELETE SUBSCRIPTION
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
