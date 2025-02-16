package tn.esprit.sporty.Chat.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Authentification.model.User;
import tn.esprit.sporty.Message.module.message;

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
