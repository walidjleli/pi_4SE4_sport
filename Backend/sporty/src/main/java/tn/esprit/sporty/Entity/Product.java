package tn.esprit.sporty.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Data

public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idProduct;
    String productName;
    String description;
    @Column(name = "stock_quantity")
    int stockquantity;
    @Column(name = "creation_date")
    LocalDate creationDate;
    String brand;
    float price;
    @Enumerated(EnumType.STRING)
    FootballProductsCategory footballProductsCategory;
    @JsonIgnore
    @ManyToOne
    Shop shop;
    // One product can have many comments
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;




}
