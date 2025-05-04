package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.PushupResult;
import tn.esprit.sporty.Entity.User;

import java.util.List;
import java.util.Optional;

public interface PushupResultRepository extends JpaRepository<PushupResult, Long> {
    Optional<PushupResult> findByUser(User user);
    List<PushupResult> findAllByOrderByBestScoreDesc();
}
