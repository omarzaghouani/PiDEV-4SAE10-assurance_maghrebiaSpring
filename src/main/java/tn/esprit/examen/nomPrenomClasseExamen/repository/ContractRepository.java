package tn.esprit.examen.nomPrenomClasseExamen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Contract;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatus;

import java.util.Date;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {


    @Query("SELECT c FROM Contract c WHERE " +
            "(c.startDate >= :startDate OR :startDate IS NULL) AND " +
            "(c.endDate <= :endDate OR :endDate IS NULL) AND " +
            "(c.status = :status OR :status IS NULL) AND " +
            "(c.number LIKE %:number% OR :number IS NULL)")
    List<Contract> findByAdvancedSearch(Date startDate, Date endDate, String status, String number);

    @Query("SELECT COUNT(c) FROM Contract c WHERE c.status = :status")
    long countByStatus(@Param("status") ContractStatus status);



}
