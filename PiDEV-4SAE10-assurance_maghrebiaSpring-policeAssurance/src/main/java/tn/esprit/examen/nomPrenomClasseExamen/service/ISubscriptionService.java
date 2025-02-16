package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import java.util.List;

public interface ISubscriptionService {
    Subscription addSubscription(Subscription s);
    Subscription updateSubscription(Long id, Subscription subscriptionEntity);
    void deleteSubscription(Long id);
    List<Subscription> getAllSubscriptions();
    Subscription getSubscriptionById(Long id);
}

