package tn.esprit.sporty.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int teamId;
    String teamName;
    @JsonManagedReference // Serialize this side

    @OneToMany(mappedBy = "team")
    List<Subgroup> subgroups;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> players = new ArrayList<>();


    public List<User> getPlayers() {
        return players;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id")
    User coach;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    User doctor;
}
