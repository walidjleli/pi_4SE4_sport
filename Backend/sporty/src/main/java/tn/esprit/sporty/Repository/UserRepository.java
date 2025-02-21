package tn.esprit.sporty.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);




}

