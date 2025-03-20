package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

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

    @NotBlank
    private String offerName;

    private String offerDetails;
    private double discountRate;
    private String validityPeriod;

    @ManyToOne
    @JoinColumn(name = "partnership_id", nullable = false)
    @JsonIgnoreProperties("offers")  // ignore back reference
    private Partnership partnership;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false) // NEW RELATIONSHIP
    @JsonIgnoreProperties("offers")  // adjust depending on how your entities relate
    private Package aPackage;

}
