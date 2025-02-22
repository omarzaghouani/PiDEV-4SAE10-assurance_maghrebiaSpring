package tn.esprit.examen.nomPrenomClasseExamen.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudInvestigation;
import tn.esprit.examen.nomPrenomClasseExamen.repository.FraudInvestigationRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FraudInvestigationService implements  IFraudInvestigationService{
  @Autowired
  FraudInvestigationRepo fraudInvestigationRepo;

  @Override
  public FraudInvestigation addFraudInvestigation(FraudInvestigation fraudInvestigation) {
    return fraudInvestigationRepo.save(fraudInvestigation);
  }

  @Override
  public Optional<FraudInvestigation> getFraudInvestigationById(int id) {
    return fraudInvestigationRepo.findById(id);
  }

  @Override
  public List<FraudInvestigation> getAllFraudInvestigations() {
    return fraudInvestigationRepo.findAll();
  }

  @Override
  public FraudInvestigation updateFraudInvestigation(int id, FraudInvestigation fraudInvestigation) {
    Optional<FraudInvestigation> existingInvestigation = fraudInvestigationRepo.findById(id);
    if (existingInvestigation.isPresent()) {
      FraudInvestigation investigation = existingInvestigation.get();
    //  investigation.setUser_Id(fraudInvestigation.getUser_Id());
      investigation.setStatus(fraudInvestigation.getStatus());
      investigation.setDetectedBy(fraudInvestigation.getDetectedBy());
     
      return fraudInvestigationRepo.save(investigation);
    }
    return null;
  }

  @Override
  public void deleteFraudInvestigation(int id) {
    fraudInvestigationRepo.deleteById(id);
  }

}
