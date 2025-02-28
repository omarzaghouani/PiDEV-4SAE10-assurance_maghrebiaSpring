package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    @Modifying
    @Query("UPDATE Package p SET p.discountedPrice = :newPrice WHERE p.id = :id")
    void updateDiscountedPrice(@Param("id") Long id, @Param("newPrice") Double newPrice);
}
