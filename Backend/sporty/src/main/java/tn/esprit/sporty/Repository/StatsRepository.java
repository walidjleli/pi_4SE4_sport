package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.sporty.Entity.Stats;

import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {
    Optional<Stats> findByUserId(Long userId);
}
