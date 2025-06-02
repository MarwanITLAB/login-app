package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendeBestaetigungsEmail(String empfaenger, String betreff, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(empfaenger);
        message.setSubject(betreff);
        message.setText(text);
        message.setFrom("deinname@web.de"); // optional: explizit Absender setzen
        mailSender.send(message);
    }
}
