package tn.esprit.sporty.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // ✅ Ajout de l'encodeur

    // ✅ Générer et envoyer un token de réinitialisation
    public boolean sendResetPasswordToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("❌ Aucun utilisateur trouvé avec cet email !");
            return false;
        }

        // ✅ Générer un token unique et stocker en base
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // ✅ Envoyer l’email avec le lien de réinitialisation
        String subject = "🔑 Réinitialisation de votre mot de passe";
        String message = "Bonjour " + user.getFirstName() + ",\n\n"
                + "Veuillez cliquer sur le lien ci-dessous pour réinitialiser votre mot de passe :\n"
                + "🔗 http://localhost:4200/reset-password?token=" + resetToken + "\n\n"
                + "Cordialement,\nVotre Équipe";

        emailServiceImpl.sendSimpleMessage(email, subject, message);
        System.out.println("✅ Email de réinitialisation envoyé à " + email);
        return true;
    }

    // ✅ Vérifier et réinitialiser le mot de passe
    public boolean resetPassword(String token, String newPassword) {
        System.out.println("🔍 Vérification du token reçu : " + token);

        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            System.out.println("❌ Token invalide ou expiré !");
            return false;
        }

        User user = userOpt.get();

        // ✅ Hash du mot de passe avant enregistrement
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        user.setResetToken(null);  // ✅ Suppression du token après usage
        userRepository.save(user);

        System.out.println("✅ Mot de passe réinitialisé avec succès !");
        return true;
    }
}
