package tn.esprit.sporty.Authentification.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {
    private String email;
    private String token;
}
