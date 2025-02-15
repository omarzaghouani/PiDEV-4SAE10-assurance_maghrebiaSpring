package tn.esprit.examen.nomPrenomClasseExamen.Entiti;




import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoice;

    private String invoiceNumber;
    private Date issueDate;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "idContrat")
    private Contract contract;  // Relation ManyToOne avec Contract

    @ManyToOne
    private Client client;
}
