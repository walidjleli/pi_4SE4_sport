package tn.esprit.sporty.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter

public class PushupResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;  // ou Fan si tu as une entité spécifique

    private int bestScore;
}
