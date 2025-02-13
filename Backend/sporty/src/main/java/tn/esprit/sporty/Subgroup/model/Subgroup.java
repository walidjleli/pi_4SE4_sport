package tn.esprit.sporty.Subgroup.model;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.sporty.Authentification.model.User;

import java.util.List;
@Entity
@Data
public class Subgroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int teamId;
    @ManyToMany
    private List<User> teamPlayers;
}
