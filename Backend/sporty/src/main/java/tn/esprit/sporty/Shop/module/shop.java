package tn.esprit.sporty.Shop.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Authentification.model.User;
import tn.esprit.sporty.Product.module.product;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
@Entity
public class shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String location;
    String contactNumber;
    Time openingHours;
    String email;
    String rating ;
    LocalDate createdAt;
    LocalDate updatedAt;
    @OneToOne
    User user;
    @OneToMany
    List<product> products;

}
