package tn.esprit.sporty.Authentification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRes {
    HttpStatus httpStatus;
    String message;
}
