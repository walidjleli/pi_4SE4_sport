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
    private int teamId;

    private String teamName;

    @JsonManagedReference
    @OneToMany(mappedBy = "team")
    private List<Subgroup> subgroups;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> players ;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "coach_id", unique = true)
    private User coach;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "doctorId", unique = true)
    private User doctor;

    public void setCoach(User coach) {
        if (coach != null && coach.getRole() != Role.COACH) {
            throw new IllegalArgumentException("L'utilisateur sélectionné n'est pas un coach.");
        }
        this.coach = coach; // ✅ Permet de mettre `null` sans erreur
    }

    public void setDoctor(User doctor) {
        if (doctor != null && doctor.getRole() != Role.DOCTOR) {
            throw new IllegalArgumentException("L'utilisateur sélectionné n'est pas un docteur.");
        }
        this.doctor = doctor; // ✅ Autoriser `null`
    }



}
