package tn.esprit.sporty.Service;


import org.springframework.security.core.userdetails.UserDetailsService;
import tn.esprit.sporty.Entity.Status;
import tn.esprit.sporty.Entity.User;

public interface UserService extends UserDetailsService {
    void createUser(User user);
    User getUserById(Integer id);
    User findByEmail(String email);
    void deleteUser(Integer  userId)  ;

}
