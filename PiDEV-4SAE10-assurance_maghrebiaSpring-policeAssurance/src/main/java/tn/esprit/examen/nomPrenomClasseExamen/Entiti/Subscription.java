package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "subscriptions")  // Ensures this class maps to the "subscriptions" table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subscriberName;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package aPackage;

    private Date startDate;
    private Date endDate;
    private String status;
}
