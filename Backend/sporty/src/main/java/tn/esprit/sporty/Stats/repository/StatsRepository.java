package tn.esprit.sporty.Stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.sporty.Stats.model.Stats;

import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {
    Optional<Stats> findByUserId(Long userId);
}
