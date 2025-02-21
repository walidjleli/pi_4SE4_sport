package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.User;

public interface EmailService {
    public void sendActivationEmail(User user);

    void sendSimpleMessage(String email, String subject, String message);
}
