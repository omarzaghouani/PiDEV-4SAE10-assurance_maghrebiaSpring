package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class FinancialManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int financialId;
    private float totalRevenue;
    private int Expenses;
    private float NetIncome;
}
