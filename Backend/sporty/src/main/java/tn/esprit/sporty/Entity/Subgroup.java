package tn.esprit.sporty.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToMany
    @JoinTable(
            name = "subgroup_users",
            joinColumns = @JoinColumn(name = "subgroup_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToMany
    private List<TrainingSession> trainingSessions;

    @ManyToOne
    @JsonBackReference  // Prevent circular references during serialization
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

}
