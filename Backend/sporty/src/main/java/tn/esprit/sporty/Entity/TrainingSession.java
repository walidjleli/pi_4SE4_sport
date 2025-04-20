package tn.esprit.sporty.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private Date startDate;
    private Date endDate;
    @ManyToMany
    @JoinTable(
            name = "subgroup_training_sessions",
            joinColumns = @JoinColumn(name = "training_sessions_id"),
            inverseJoinColumns = @JoinColumn(name = "subgroups_subgroup_id") // EXACTEMENT CE NOM
    )
    private List<Subgroup> subgroups;




    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL)
    private List<Attendance> attendances;
}
