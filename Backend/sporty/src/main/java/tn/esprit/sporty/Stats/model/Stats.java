package tn.esprit.sporty.Stats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.sporty.Authentification.model.User;

@Entity
@Data
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int goalsScored;
    private int assists;
    private int minutesPlayed;
    private int interceptions;
    private int successfulPasses;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @JsonIgnore
    private User user;
}
