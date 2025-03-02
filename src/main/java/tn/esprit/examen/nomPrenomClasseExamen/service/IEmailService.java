package tn.esprit.examen.nomPrenomClasseExamen.service;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendConfirmationEmail(String to, String subject, String text) throws MessagingException;
}
