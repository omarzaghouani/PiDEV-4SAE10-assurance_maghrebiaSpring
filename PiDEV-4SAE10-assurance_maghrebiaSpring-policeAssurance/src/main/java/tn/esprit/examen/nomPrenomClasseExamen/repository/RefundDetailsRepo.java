package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundDetails;

import java.util.List;

public interface RefundDetailsRepo extends JpaRepository<RefundDetails, Integer> {
    List<RefundDetails> findByUserId(int userId);  

}
