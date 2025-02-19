package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class FeedBack {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long FeedBackid;
    private String Message;
    private Date SentDate;


    @OneToOne
    @JsonBackReference
    private FeedbackManagementStats feedbackmanagementstats;


}