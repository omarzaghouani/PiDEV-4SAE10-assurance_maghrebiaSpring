package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import tn.esprit.examen.nomPrenomClasseExamen.repository.SubscriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok handles constructor injection
public class SubscriptionService implements ISubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription addSubscription(Subscription s) {
        return subscriptionRepository.save(s);
    }

    @Override
    public Subscription updateSubscription(Long id, Subscription subscriptionEntity) {
        /*
        return subscriptionRepository.findById(id)
                .map(existingSubscription -> {
                    existingSubscription.setUser(subscriptionEntity.getUser());
                    existingSubscription.setSubscriptionType(subscriptionEntity.getSubscriptionType());
                    existingSubscription.setStartDate(subscriptionEntity.getStartDate());
                    existingSubscription.setEndDate(subscriptionEntity.getEndDate());
                    return subscriptionRepository.save(existingSubscription);
                })
                .orElse(null);

         */
        return null;
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
