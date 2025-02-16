package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
}
