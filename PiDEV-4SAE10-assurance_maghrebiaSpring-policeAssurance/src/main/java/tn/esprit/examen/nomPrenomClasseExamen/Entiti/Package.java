package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // This ensures the enum is stored as a string in the DB
    private PackageType type;

    private String name;
    private String description;
    private String imageUrl;
    private int duration;
    // price should not be changed
    private double price;
    private Double discountedPrice; // New field for discounted price
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "apackage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Prevents infinite loop
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "aPackage", cascade = CascadeType.ALL, orphanRemoval = true) // NEW RELATIONSHIP
    private List<PartnershipOffer> partnershipOffers;
}
