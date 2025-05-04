package tn.esprit.sporty.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor  // Lombok annotation generates the no-argument constructor
@AllArgsConstructor
@Entity
@Table(name = "subgroup")
public class Subgroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subgroupId;

    private String subgroupName;

    @OneToMany(mappedBy = "subgroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> players;

    @JsonIgnore
    @ManyToMany(mappedBy = "subgroups")
    private List<TrainingSession> trainingSessions;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonBackReference
    private Team team;



}
