package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JoinColumn(name = "partnership_id", nullable = true)
    @JsonIgnoreProperties("offers")  // ignore back reference
    private Partnership partnership;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = true) // NEW RELATIONSHIP
    @JsonProperty("apackage") // 👈 forces JSON to accept/send it as "apackage"
    @JsonIgnoreProperties("offers")  // adjust depending on how your entities relate
    private Package aPackage;

}
