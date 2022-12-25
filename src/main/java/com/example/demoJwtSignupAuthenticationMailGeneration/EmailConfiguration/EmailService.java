package com.example.demoJwtSignupAuthenticationMailGeneration.EmailConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String to, String body, String subject) {
        SimpleMailMessage mimeMessage=new SimpleMailMessage();
        mimeMessage.setFrom("akashmalick2022@gmail.com");
        mimeMessage.setTo(to);
        mimeMessage.setText(body);
        mimeMessage.setSubject(subject);
        javaMailSender.send(mimeMessage);
        System.out.println("Email send to particular mail address...");
    }
}

