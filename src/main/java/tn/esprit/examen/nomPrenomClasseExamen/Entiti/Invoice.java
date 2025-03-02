package tn.esprit.examen.nomPrenomClasseExamen.Entiti;




import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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
@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "idContrat")
    private Contract contract;  // Relation ManyToOne avec Contract
    @JsonIgnore

    @ManyToOne
    private Client client;
}
