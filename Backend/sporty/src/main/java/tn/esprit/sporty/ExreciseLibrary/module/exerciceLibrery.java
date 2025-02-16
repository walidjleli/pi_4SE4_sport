package tn.esprit.sporty.ExreciseLibrary.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Exercice.module.exercice;

import java.io.Serializable;
import java.util.List;

@Entity
public class exerciceLibrery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToMany
    List<exercice> exercices;


}
