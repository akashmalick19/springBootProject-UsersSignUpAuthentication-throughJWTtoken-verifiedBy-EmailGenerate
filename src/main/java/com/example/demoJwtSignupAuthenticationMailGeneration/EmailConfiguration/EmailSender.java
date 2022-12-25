package com.example.demoJwtSignupAuthenticationMailGeneration.EmailConfiguration;

public interface EmailSender {
    void send(String to,String body,String subject);
}
