package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.sporty.Entity.Stats;

import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {
    @Query("SELECT s FROM Stats s WHERE s.player.id = :userId")

    Optional<Stats> findByUserId(Integer userId);
}
