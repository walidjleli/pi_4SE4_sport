package tn.esprit.sporty.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @JsonProperty("id")

    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    @OneToOne(mappedBy = "player")
    private Stats statistics;

    @Enumerated(EnumType.STRING)
    private Status roleStatus ;  // Ajouté pour une meilleure gestion du statut du rôle

    @Enumerated(EnumType.STRING)
    private Status activationStatus = Status.OFF; // Default to OFF

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @OneToOne
    PushupResult pushupResult;

    private String activationToken;

    @JsonIgnore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.roleStatus = Status.OFF;
    }


    public void setRoleStatus(Status status) {
        this.roleStatus=status;
    }

    private String resetToken;
    @JsonIgnore

    @ManyToOne
    @JoinColumn(name = "subgroup_id")
    private Subgroup subgroup;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Attendance> attendances = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private Poste poste;

}