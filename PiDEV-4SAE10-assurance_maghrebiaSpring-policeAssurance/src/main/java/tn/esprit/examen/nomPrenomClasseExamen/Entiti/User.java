package tn.esprit.examen.nomPrenomClasseExamen.Entiti;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
  //  private String firstName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "User", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RefundDetails> refundDetailsList;
*/
    
    
   
}

