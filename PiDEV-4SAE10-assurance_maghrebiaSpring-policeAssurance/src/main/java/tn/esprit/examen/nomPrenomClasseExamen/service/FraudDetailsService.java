package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FraudDetailsRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FraudDetailsService  implements IFraudDetailsService{
  @Autowired
  FraudDetailsRepo fraudDetailsRepo;

  @Override
  public FraudDetails addFraudDetails(FraudDetails fraudDetails) {
    return fraudDetailsRepo.save(fraudDetails);
  }

  @Override
  public Optional<FraudDetails> getFraudDetailsById(int id) {
    return fraudDetailsRepo.findById(id);
  }

  @Override
  public List<FraudDetails> getAllFraudDetails() {
    return fraudDetailsRepo.findAll();
  }

  @Override
  public FraudDetails updateFraudDetails(int id, FraudDetails fraudDetails) {
    Optional<FraudDetails> existingFraud = fraudDetailsRepo.findById(id);
    if (existingFraud.isPresent()) {
      FraudDetails fraud = existingFraud.get();
      fraud.setFraudType(fraudDetails.getFraudType());
      fraud.setRiskScore(fraudDetails.getRiskScore());
      fraud.setActionTaken(fraudDetails.getActionTaken());
      return fraudDetailsRepo.save(fraud);
    }
    return null;
  }

  @Override
  public void deleteFraudDetails(int id) {
    fraudDetailsRepo.deleteById(id);
  }
}
