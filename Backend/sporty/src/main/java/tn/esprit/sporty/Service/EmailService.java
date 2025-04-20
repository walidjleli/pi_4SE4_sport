package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.User;

public interface EmailService {
    public void sendActivationEmail(User user);

    void sendSimpleMessage(String to, String subject, String text);
    void sendAbsenceNotification(String email);
}
