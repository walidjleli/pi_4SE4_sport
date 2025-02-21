package tn.esprit.sporty.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.sporty.Entity.Role;
import tn.esprit.sporty.Entity.Status;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
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

        // Mettre le roleStatus à OFF si l'utilisateur est un DOCTOR ou un COACH
        if (user.getRole() == Role.DOCTOR || user.getRole() == Role.COACH) {
            user.setRoleStatus(Status.OFF);

        } else {
            user.setRoleStatus(Status.ON); // Sinon, activé par défaut
        }

        userRepository.save(user);
    }


    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UsernameNotFoundException("User with ID " + userId + " not found.");
        }
    }

    public boolean updateUserActivationStatus(Long userId, Status activationStatus) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setActivationStatus(activationStatus);  // Mettre à jour le statut (ON ou OFF)
            userRepository.save(user);  // Sauvegarder les modifications
            return true;
        }
        return false; // Retourner false si l'utilisateur n'existe pas
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
            user.setRoleStatus(Status.valueOf(status));
            userRepository.save(user);
            return true;
        }
        return false;
    }



}