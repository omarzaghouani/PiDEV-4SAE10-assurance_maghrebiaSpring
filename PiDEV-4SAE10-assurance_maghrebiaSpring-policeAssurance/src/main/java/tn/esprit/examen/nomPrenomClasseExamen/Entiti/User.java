package tn.esprit.examen.nomPrenomClasseExamen.Entiti;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefundDetails> refundDetailsList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FraudInvestigation> fraudInvestigations;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FeedBack> FeedBacks;


  // Getters and setters
}

