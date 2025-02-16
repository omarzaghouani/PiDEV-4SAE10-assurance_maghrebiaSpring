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
public class FraudDetails {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int fraudCaseId;

  private String fraudType;
  private float riskScore;
  private String actionTaken;

  @OneToOne(mappedBy = "fraudDetails", cascade = CascadeType.ALL)
  private FraudInvestigation fraudInvestigation;

}
