package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "partnerships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Partnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String contactEmail;
    private String phoneNumber;
    private String industry;
    private String agreementDetails;

    @OneToMany(mappedBy = "partnership", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnershipOffer> offers;
}
