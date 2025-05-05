package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.sporty.Entity.Health;

@Repository
public interface HealthRepo extends JpaRepository<Health, Long> {


    Health findHealthByUserCondition(String condition);

    
}
