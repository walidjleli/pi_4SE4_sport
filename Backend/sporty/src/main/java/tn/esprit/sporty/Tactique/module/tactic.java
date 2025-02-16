package tn.esprit.sporty.Tactique.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Exercice.module.exercice;

import java.io.Serializable;

@Entity
public class tactic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    @ManyToOne
    exercice exercice;

}
