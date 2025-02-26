package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundAudit;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.RefundStatus;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)

@Entity
public class RefundDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int refundId;

  private  int userId;
  private int orderId;
  private double amount;
  private String reason;
  private LocalDateTime createdAt;
  private LocalDateTime processedAt;
  
  
  

  @Enumerated(EnumType.STRING)
  private RefundStatus refundStatus;

  @OneToOne(mappedBy = "refundDetails", cascade = CascadeType.ALL)
  @JsonManagedReference
  private RefundAudit refundAudit;
/*
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;*/

  @OneToMany(mappedBy = "refundDetails", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<FraudInvestigation> fraudInvestigations;



}
