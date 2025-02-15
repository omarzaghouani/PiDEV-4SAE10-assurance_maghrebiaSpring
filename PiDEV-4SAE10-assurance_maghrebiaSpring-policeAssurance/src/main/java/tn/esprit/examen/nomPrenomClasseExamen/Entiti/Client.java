package tn.esprit.examen.nomPrenomClasseExamen.Entiti;



import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    // Getters and setters
}

