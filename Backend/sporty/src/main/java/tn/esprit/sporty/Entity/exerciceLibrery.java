package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

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
