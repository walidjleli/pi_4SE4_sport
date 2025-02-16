package tn.esprit.sporty.Order.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Facture.module.facture;
import tn.esprit.sporty.Product.module.product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Entity
public class order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Date orderDate;
    @Enumerated
    statut statut;
    float total;
    String paymentMode;
    String location;
    LocalDate arrivalDateEstimation;
    @OneToMany
    List<product> products;
    @OneToOne
    facture facture;


}
