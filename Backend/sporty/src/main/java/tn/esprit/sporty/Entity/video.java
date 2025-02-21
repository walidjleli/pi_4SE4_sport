package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class video implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String url;
    String title;
    String description;
    @ManyToOne
    TrainingSession trainingSession;
}
