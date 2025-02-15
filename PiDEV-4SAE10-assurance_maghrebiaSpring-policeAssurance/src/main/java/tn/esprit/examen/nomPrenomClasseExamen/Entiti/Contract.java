package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.User;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractDocument;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.ContractStatus;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.Invoice;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    private String number;
    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @ManyToOne
    private User User;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "idDoc")
    private ContractDocument document;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices;  // Relation OneToMany avec Invoice
}
