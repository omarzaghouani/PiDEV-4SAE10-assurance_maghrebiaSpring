package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractDocument;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudDetails;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FraudInvestigation;

import java.util.Optional;

public interface FraudDetailsRepo extends JpaRepository<FraudDetails, Integer> {
    Optional<FraudDetails> findByFraudInvestigation(FraudInvestigation fraudInvestigation);

    Optional<FraudDetails> findByFraudCaseId(int fraudCaseId);

    int countByFraudInvestigation(FraudInvestigation fraudInvestigation);

    @Query("SELECT COUNT(fd) FROM FraudDetails fd " +
            "JOIN fd.fraudInvestigation fi " +
            "JOIN fi.refundDetails rd " +
            "WHERE rd.userId = :userId")
    int countByUserId(@Param("userId") int userId);}
