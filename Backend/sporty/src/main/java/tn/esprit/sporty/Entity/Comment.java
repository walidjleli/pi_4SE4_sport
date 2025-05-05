package tn.esprit.sporty.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Data

public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private int likes;
    private int dislikes;

    // Many comments belong to one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("comments")  // Foreign key column for product
    private Product product;
}
