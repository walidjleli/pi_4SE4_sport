package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.TrainingSession;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer> {
}
