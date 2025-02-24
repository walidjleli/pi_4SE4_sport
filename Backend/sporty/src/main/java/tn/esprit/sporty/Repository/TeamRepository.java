package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.Team;

public interface TeamRepository extends JpaRepository<Team,Integer> {

}
