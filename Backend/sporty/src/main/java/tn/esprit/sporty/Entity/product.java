package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;

@Entity
public class product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    float price ;
    int stockQuantity;
    String categorie;
    String brand;
    @ManyToOne
    shop shop;
    Time createdAt;
    Time updatedAt;
    @ManyToOne
    order order;

}
