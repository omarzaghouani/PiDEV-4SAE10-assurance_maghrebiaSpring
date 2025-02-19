package tn.esprit.examen.nomPrenomClasseExamen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sinistre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int clientId;

    @Enumerated(EnumType.STRING)
    private TypeSinistre typeSinistre;

    private double montantEstime;

    @Enumerated(EnumType.STRING)
    private StatutSinistre statutSinistre;

    private Date dateDeclaration;
    private Date dateValidation;
}
