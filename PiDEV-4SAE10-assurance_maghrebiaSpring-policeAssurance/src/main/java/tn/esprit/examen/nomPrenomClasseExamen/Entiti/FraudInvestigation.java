package tn.esprit.examen.nomPrenomClasseExamen.Entiti;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
  private int fraudCaseId;
  
  private String detectedBy;
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime resolvedAt;

  @Enumerated(EnumType.STRING)
  private FraudStatus status;

  @OneToOne(mappedBy = "fraudInvestigation", cascade = CascadeType.ALL) 
  @JsonManagedReference
  private FraudDetails fraudDetails;
  
  @ManyToOne
  @JoinColumn(name = "refund_id")
  @JsonBackReference
  private RefundDetails refundDetails;


}
