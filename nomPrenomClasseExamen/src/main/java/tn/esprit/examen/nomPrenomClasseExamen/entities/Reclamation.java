package tn.esprit.examen.nomPrenomClasseExamen.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int clientId;

    @Enumerated(EnumType.STRING)
    private TypeReclamation typeReclamation;  // ✅ Correction du nom de l'attribut

    private String description;

    @Enumerated(EnumType.STRING)
    private StatutReclamation statutReclamation; // ✅ Correction du nom de l'attribut

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTraitement;
}
