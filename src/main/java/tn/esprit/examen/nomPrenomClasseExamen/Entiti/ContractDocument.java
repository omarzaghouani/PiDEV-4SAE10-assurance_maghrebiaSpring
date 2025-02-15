package tn.esprit.examen.nomPrenomClasseExamen.Entiti;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class ContractDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoc;
    private String fileName;
    private String path;
    private String type;

    @OneToOne(mappedBy = "document")
    private Contract contract;

    // Getters and setters
}

