package tn.esprit.sporty.Product.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Order.module.order;
import tn.esprit.sporty.Shop.module.shop;

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
