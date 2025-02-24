package tn.esprit.sporty.Entity;


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
    @JsonProperty("id")  // Ensure correct mapping

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
// Utilisé pour activer/désactiver l'utilisateur

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ManyToMany(mappedBy = "players", fetch = FetchType.EAGER)
    private List<Team> teams = new ArrayList<>();


    private String activationToken;

    @JsonIgnore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.roleStatus = Status.OFF;
    }


    public void setRoleStatus(Status status) {
        this.roleStatus=roleStatus;
    }

    private String resetToken;

    public boolean isPartOfTeam(int teamId) {
        return this.teams.stream().anyMatch(team -> team.getTeamId() == teamId);
    }
}
