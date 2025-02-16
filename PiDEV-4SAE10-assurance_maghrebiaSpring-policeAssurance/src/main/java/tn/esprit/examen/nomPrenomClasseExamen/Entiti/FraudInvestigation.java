package tn.esprit.examen.nomPrenomClasseExamen.Entiti;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class FraudInvestigation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int fraudCaseId;
  private String detectedBy;
  private LocalDateTime createdAt;
  private LocalDateTime resolvedAt;

  @Enumerated(EnumType.STRING)
  private FraudStatus status;

  @OneToOne
  @JoinColumn(name = "fraud_case_ref_id")
  private FraudDetails fraudDetails;
  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
