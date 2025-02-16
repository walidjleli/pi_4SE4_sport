package tn.esprit.sporty.Authentification.model;
import tn.esprit.sporty.Team.module.team;


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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)

    private Status status;
    @Enumerated(EnumType.STRING)

    private Role role;
    @ManyToMany(mappedBy = "users")


    private List<team> teams;
    @JsonIgnore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
