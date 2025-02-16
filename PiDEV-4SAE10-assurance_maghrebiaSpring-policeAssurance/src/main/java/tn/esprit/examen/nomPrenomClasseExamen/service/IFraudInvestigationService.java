package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudInvestigation;

import java.util.List;
import java.util.Optional;

public interface IFraudInvestigationService {
  FraudInvestigation addFraudInvestigation(FraudInvestigation fraudInvestigation);

  Optional<FraudInvestigation> getFraudInvestigationById(int id);

  List<FraudInvestigation> getAllFraudInvestigations();

  FraudInvestigation updateFraudInvestigation(int id, FraudInvestigation fraudInvestigation);

  void deleteFraudInvestigation(int id);
}
