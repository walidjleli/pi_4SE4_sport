package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.Subgroup;

import java.util.List;

public interface SubgroupRepository extends JpaRepository<Subgroup, Integer> {
    List<Subgroup> findByTeam_TeamId(int teamId);


    boolean existsBySubgroupName(String subgroupName);
}
