package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToMany
    List<User> users;
    @OneToMany
    List<message> messages;
}
