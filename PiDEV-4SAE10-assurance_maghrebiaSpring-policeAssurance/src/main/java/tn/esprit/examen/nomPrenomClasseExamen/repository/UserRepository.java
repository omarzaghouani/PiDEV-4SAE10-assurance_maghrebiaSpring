package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
