package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Subgroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    int subgroupId;
    String subgroupName;
    @ManyToMany
    List<TrainingSession> trainingSessions;
    @ManyToOne
    team team;


}
