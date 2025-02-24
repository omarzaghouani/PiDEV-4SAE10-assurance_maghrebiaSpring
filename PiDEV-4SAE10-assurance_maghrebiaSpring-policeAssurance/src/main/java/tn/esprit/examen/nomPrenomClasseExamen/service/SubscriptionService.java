package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import tn.esprit.examen.nomPrenomClasseExamen.repository.SubscriptionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {


    private final SubscriptionRepository subscriptionRepository;

    public Subscription addSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(id);
        if (existingSubscription.isPresent()) {
            Subscription updatedSub = existingSubscription.get();
            updatedSub.setSubscriberName(subscription.getSubscriberName());
            updatedSub.setStartDate(subscription.getStartDate());
            updatedSub.setEndDate(subscription.getEndDate());
            updatedSub.setStatus(subscription.getStatus());
            updatedSub.setApackage(subscription.getApackage()); // ✅ Ensuring the correct field
            return subscriptionRepository.save(updatedSub);
        }
        return null;
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
