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
public class RefundAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int processedBy;
  private LocalDateTime processedAt;
  private String auditReason;

  @OneToOne
  @JoinColumn(name = "refund_id")
  private RefundDetails refundDetails;
}
