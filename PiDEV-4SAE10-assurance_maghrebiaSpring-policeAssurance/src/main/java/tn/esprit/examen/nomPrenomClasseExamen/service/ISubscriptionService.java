package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Subscription;
import java.util.List;

public interface ISubscriptionService {
    Subscription addSubscription(Subscription subscription);
    Subscription updateSubscription(Long id, Subscription subscription);
    void deleteSubscription(Long id);
    List<Subscription> getAllSubscriptions();
    Subscription getSubscriptionById(Long id);
}

