package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

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
    tn.esprit.sporty.Entity.statut statut;
    float total;
    String paymentMode;
    String location;
    LocalDate arrivalDateEstimation;
    @OneToMany
    List<product> products;
    @OneToOne
    facture facture;


}
