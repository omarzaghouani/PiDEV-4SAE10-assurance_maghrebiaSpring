package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.FeedBack;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

    @Query("SELECT f FROM FeedBack f WHERE " +
            "LOWER(f.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.productService) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.sentimentAnalysis) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR CAST(f.satisfactionScore AS string) LIKE CONCAT('%', :keyword, '%')")
    List<FeedBack> rechercheGlobale(@Param("keyword") String keyword);

}
