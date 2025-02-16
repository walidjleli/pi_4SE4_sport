package tn.esprit.sporty.TrainingSession.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Subgroup.model.Subgroup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class TrainingSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    Date startDate;
    Date endDate;
    @ManyToMany
    List<Subgroup> subgroups;
}
