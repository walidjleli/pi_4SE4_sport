package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class exercice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    String name;
    String description;
    int duration;
    @ManyToMany
    List<TrainingSession> trainingSessions;

}
