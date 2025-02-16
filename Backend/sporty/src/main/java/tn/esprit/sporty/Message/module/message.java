package tn.esprit.sporty.Message.module;

import jakarta.persistence.*;
import tn.esprit.sporty.Chat.module.chat;

import java.io.Serializable;
import java.util.Date;

@Entity
public class message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;
    String content;
    Date date;
    @ManyToOne
    chat chat;
}
