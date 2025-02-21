package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

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
