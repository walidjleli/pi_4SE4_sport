package tn.esprit.sporty.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.esprit.sporty.Entity.Role;
import tn.esprit.sporty.Entity.Status;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if an admin already exists
        if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
            System.out.println("Creating default admin user...");

            User adminUser = new User();
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("Admin@123")); // Encode password
            adminUser.setFirstName("Super");
            adminUser.setLastName("Admin");
            adminUser.setRole(Role.ADMIN);
            adminUser.setActivationStatus(Status.ON); // Admin is active by default

            userRepository.save(adminUser);
            System.out.println("Admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
