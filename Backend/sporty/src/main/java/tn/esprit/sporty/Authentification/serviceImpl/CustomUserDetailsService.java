package tn.esprit.sporty.Authentification.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Authentification.model.Role;
import tn.esprit.sporty.Authentification.model.Status;
import tn.esprit.sporty.Authentification.model.User;
import tn.esprit.sporty.Authentification.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        //List<String> roles = new ArrayList<>();
        //roles.add("USER");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(String.valueOf(user.getRole()))
                        .build();
        return userDetails;
    }
    /////////////////////////////////////////////////
    public void createUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("L'email est déjà utilisé.");
        }


        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public Role getUserRoleByEmail(String email) {
        User user = userRepository.findByEmail(email); // Assuming userRepository is available
        if (user != null) {
            return user.getRole(); // Assuming the User model has a method to get the role
        } else {
            return null;
        }
    }

    public boolean updateUserStatus(Long userId, String status) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setStatus(Status.valueOf(status));
            userRepository.save(user);
            return true;
        }
        return false;
    }

}