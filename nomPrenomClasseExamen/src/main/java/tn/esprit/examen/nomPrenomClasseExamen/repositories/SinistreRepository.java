package tn.esprit.examen.nomPrenomClasseExamen.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Sinistre;

public interface SinistreRepository extends JpaRepository<Sinistre, Integer> {
}

