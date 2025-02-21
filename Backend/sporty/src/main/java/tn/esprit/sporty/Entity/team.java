package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

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
