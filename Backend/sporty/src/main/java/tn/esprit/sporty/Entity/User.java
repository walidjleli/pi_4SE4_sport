package tn.esprit.sporty.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Status roleStatus = Status.OFF;  // Ajouté pour une meilleure gestion du statut du rôle

    @Enumerated(EnumType.STRING)
    private Status activationStatus; // Utilisé pour activer/désactiver l'utilisateur

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "users")
    private List<team> teams;

    private String activationToken;

    @JsonIgnore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.roleStatus = Status.OFF;  // Définir l'utilisateur comme inactif par défaut
    }





    public void setRoleStatus(Status status) {
        this.roleStatus=roleStatus;
    }

    private String resetToken;
}
