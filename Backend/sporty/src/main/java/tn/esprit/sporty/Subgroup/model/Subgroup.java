package tn.esprit.sporty.Subgroup.model;

import jakarta.persistence.*;
import tn.esprit.sporty.Team.module.team;
import tn.esprit.sporty.TrainingSession.module.TrainingSession;

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
