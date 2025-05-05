package tn.esprit.sporty.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity

public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    Long idShop;
    String nom;
    String location;
    String ContactNumber;
    String openingHours;
    String  closinghours;
    String email;
    String description;
    @Enumerated(EnumType.STRING)
    Shopcategorie categorie;
    
    @OneToMany(mappedBy = "shop",orphanRemoval = true)
    List<Product> productList;
}
