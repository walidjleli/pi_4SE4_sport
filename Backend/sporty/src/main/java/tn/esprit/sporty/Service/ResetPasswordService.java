package tn.esprit.sporty.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.sporty.Entity.User;
import tn.esprit.sporty.Repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    // Générer et envoyer un token de réinitialisation
    public boolean sendResetPasswordToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        // Générer un token unique
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // Envoyer le token par email
        String subject = "Réinitialisation de votre mot de passe";
        String message = "Cliquez sur ce lien pour réinitialiser votre mot de passe : " +
                "http://localhost:8090/rest/auth/reset-password?token=" + resetToken;

        emailServiceImpl.sendSimpleMessage(email, subject, message);
        return true;
    }

    // Vérifier et réinitialiser le mot de passe
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        user.setPassword(newPassword);  // Hacher le mot de passe avant de l’enregistrer
        user.setResetToken(null);  // Supprimer le token après utilisation
        userRepository.save(user);
        return true;
    }
}
