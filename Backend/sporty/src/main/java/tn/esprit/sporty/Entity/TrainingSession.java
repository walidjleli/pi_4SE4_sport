package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

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
    @ManyToMany(mappedBy = "trainingSessions")
    List<Subgroup> subgroups;
}
