package com.canama.websocket.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.canama.websocket.chat.model.Message;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    // Für die öffentliche Nachrichtenübertragung wird die Methode recivePublicMessage verwendet.
    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public") // /chatroom/public
    // Das @Payload-Annotation wird verwendet, um die Nachricht zu extrahieren, die vom Client gesendet wurde.
    private Message recivePublicMessage(@Payload Message message) {
        return message;
    }

    // Für die private Nachrichtenübertragung wird die Methode recivePrivateMessage verwendet.
    @MessageMapping("/private") // /app/private
    public Message recivePrivateMessage(@Payload Message message) {



        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/{username}/private
        return message;
    }

}
