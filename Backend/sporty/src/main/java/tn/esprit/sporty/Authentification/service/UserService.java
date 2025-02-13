package tn.esprit.sporty.Authentification.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.sporty.Authentification.model.User;

public interface UserService extends UserDetailsService {
    User createUser(User user);
    User getUserById(Long id);
    User getUserByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
