package com.nikhil.tradingadvisory.emailService;

import com.nikhil.tradingadvisory.samco.model.ReferenceData;

import java.util.List;

public interface EmailSender {
    void sendRegistrationEmail(String to, String email);
    void sendSuggestionMail(ReferenceData referenceData);
    String buildConfirmationEmail(String name, String link);
}
