package tn.esprit.sporty.Team.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Authentification.model.User;
import tn.esprit.sporty.Subgroup.model.Subgroup;

import java.io.Serializable;
import java.util.List;
@Entity
public class team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int teamId;
    String teamName;
    @OneToMany
    List<Subgroup> subgroups;
    @ManyToMany
    List<User>users;

}
