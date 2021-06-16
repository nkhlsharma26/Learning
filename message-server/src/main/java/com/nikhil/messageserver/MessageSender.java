package com.nikhil.messageserver;

import com.nikhil.messageserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    @Qualifier("emailService")
    private MessageService emailService;

    public void sendMessage(){
        emailService.sendMessage();
    }
}
