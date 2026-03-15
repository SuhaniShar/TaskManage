package com.TaskManage.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String toEmail, String token) {
        String resetUrl = "http://localhost:5050/api/Authentication/rest_password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset your password");
        message.setText("Click the link to reset your password:\n" + resetUrl);

        mailSender.send(message);
    }
}