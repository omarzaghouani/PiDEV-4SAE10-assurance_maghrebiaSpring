package tn.esprit.examen.nomPrenomClasseExamen.Entiti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.examen.nomPrenomClasseExamen.Entiti.User;
import java.time.LocalDateTime;

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
    private Long feedbackId;

    @ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user; // Reference to the user who provided the feedback

    private LocalDateTime submissionDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private Integer satisfactionScore; // Rating (e.g., out of 5 or 10)

    private String productService; // Concerned insurance product or service

    private String sentimentAnalysis; // Sentiment analysis (Positive, Neutral, Negative)
}
