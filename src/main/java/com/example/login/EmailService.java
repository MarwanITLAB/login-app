package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("marwan.arefi@web.de"); // ✅ Setze deine Web.de-Adresse als Absender
        message.setSubject("Willkommen bei der Login-App!");
        message.setText("Hallo " + username + ",\n\nvielen Dank für deine Registrierung!");

        mailSender.send(message);
    }
}
