package tn.esprit.sporty.Authentification.model;



import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    private String role;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
