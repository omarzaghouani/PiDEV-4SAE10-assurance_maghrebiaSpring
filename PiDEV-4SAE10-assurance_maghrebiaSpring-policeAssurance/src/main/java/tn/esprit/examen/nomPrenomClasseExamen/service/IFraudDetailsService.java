package tn.esprit.examen.nomPrenomClasseExamen.service;

import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;

import java.util.List;
import java.util.Optional;

public interface IFraudDetailsService {
  FraudDetails addFraudDetails(FraudDetails fraudDetails);

  Optional<FraudDetails> getFraudDetailsById(int id);

  List<FraudDetails> getAllFraudDetails();

  FraudDetails updateFraudDetails(int id, FraudDetails fraudDetails);

  void deleteFraudDetails(int id);

  Optional<FraudDetails> getFraudDetailsByFraudCaseId(int fraudCaseId);
  
}
