package com.nikhil.tradingadvisory.emailService;

public interface EmailSender {
    void sendEmail(String to, String email);
    String buildConfirmationEmail(String name, String link);
}
