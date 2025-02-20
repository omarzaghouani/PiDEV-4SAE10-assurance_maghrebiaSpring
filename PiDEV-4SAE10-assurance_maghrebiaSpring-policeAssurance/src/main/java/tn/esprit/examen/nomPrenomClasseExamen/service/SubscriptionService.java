package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import tn.esprit.examen.nomPrenomClasseExamen.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription addSubscription(Subscription subscription) {
        if (subscription.getAPackage() == null) {
            throw new IllegalArgumentException("Package cannot be null.");
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public Subscription updateSubscription(Long id, Subscription subscriptionEntity) {
        return subscriptionRepository.findById(id)
                .map(existingSubscription -> {
                    existingSubscription.setSubscriberName(subscriptionEntity.getSubscriberName());
                    existingSubscription.setStartDate(subscriptionEntity.getStartDate());
                    existingSubscription.setEndDate(subscriptionEntity.getEndDate());
                    existingSubscription.setStatus(subscriptionEntity.getStatus());
                    existingSubscription.setAPackage(subscriptionEntity.getAPackage());
                    return subscriptionRepository.save(existingSubscription);
                })
                .orElse(null);
    }

    @Override
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }
}
