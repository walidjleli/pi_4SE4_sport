package tn.esprit.sporty.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Role;
import tn.esprit.sporty.Entity.Status;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }

        // Ajouter des rôles sous forme d'authorities
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())// Utiliser le nom du rôle
                .build();
    }

    /////////////////////////////////////////////////
    public void createUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("L'email est déjà utilisé.");
        }

        // Mettre le roleStatus à OFF si l'utilisateur est un DOCTOR ou un COACH
        if (user.getRole() == Role.DOCTOR || user.getRole() == Role.COACH) {
            user.setRoleStatus(Status.OFF);
        } else {
            user.setRoleStatus(Status.ON); // Sinon, activé par défaut
        }

        userRepository.save(user);
    }


    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Utilisateur avec l'ID " + id + " non trouvé."));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UsernameNotFoundException("User with ID " + userId + " not found.");
        }
    }

    @Override
    public List<User> findUsersWithoutSubgroup(int teamId) {
        return List.of();
    }


    public Role getUserRoleByEmail(String email) {
        User user = userRepository.findByEmail(email); // Assuming userRepository is available
        if (user != null) {
            return user.getRole(); // Assuming the User model has a method to get the role
        } else {
            return null;
        }

    }




}