package tn.esprit.sporty.Entity;

import jakarta.persistence.*;

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
