package tn.esprit.sporty.Authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.sporty.Authentification.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);




}

