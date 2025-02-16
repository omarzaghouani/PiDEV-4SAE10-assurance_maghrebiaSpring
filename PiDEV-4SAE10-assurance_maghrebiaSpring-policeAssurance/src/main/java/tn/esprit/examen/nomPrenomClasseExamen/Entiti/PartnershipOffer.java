package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partnership_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnershipOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String offerName;
    private String offerDetails;
    private double discountRate;
    private String validityPeriod;

    @ManyToOne
    @JoinColumn(name = "partnership_id", nullable = false)
    private Partnership partnership;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false) // NEW RELATIONSHIP
    private Package aPackage;

}
