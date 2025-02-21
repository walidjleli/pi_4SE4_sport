package tn.esprit.sporty.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmailRequest {
    private String email;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}