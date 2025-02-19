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
public class FeedbackManagementStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long StatId;
    private long feedbackId;

    @Enumerated(EnumType.STRING)
    private StatsType statsValue;
}