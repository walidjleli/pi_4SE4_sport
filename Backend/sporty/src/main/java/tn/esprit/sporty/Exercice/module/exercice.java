package tn.esprit.sporty.Exercice.module;

import jakarta.persistence.*;
import tn.esprit.sporty.TrainingSession.module.TrainingSession;

import java.io.Serializable;
import java.time.LocalDate;
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
