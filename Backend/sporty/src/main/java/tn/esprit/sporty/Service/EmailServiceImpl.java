package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Autowired
    private JavaMailSender mailSender;

    // Change the URL to match your application settings
    private final String activationUrlBase = "http://localhost:8090/rest/auth/activate";

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendActivationEmail(User user) {
        String activationLink = activationUrlBase + "?email=" + user.getEmail()
                + "&token=" + user.getActivationToken();
        String subject = "Activate your account";
        String message = "Dear " + user.getFirstName() + ",\n\n"
                + "Please click the link below to activate your account:\n"
                + activationLink + "\n\n"
                + "Regards,\nYour Team";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    @Override
    public void sendAbsenceNotification(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Absence à l'entraînement");
        message.setText("Bonjour, vous avez été marqué absent à la dernière session. Merci de contacter le coach.");
        mailSender.send(message);
    }



}
