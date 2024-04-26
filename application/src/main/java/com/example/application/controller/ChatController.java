package com.example.application.controller;

import com.example.application.model.ChatMessage;
import com.example.application.model.User;
import com.example.application.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessageViaSocket(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @PostMapping("/chat.sendMessage")
    public ResponseEntity<ChatMessage> sendMessageViaPost(@RequestBody ChatMessage chatMessage) {
        ChatMessage createdMessage = messageService.createMessage(chatMessage);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/chat.getMessages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        List<ChatMessage> messages = messageService.getMessages();
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/join-chat")
    public ResponseEntity<String> joinChat() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String responseMessage = "Ansluten till chatten! Välkommen, " + username + "!";
        return ResponseEntity.ok(responseMessage);
    }
    @GetMapping("/test-websocket")
    public ResponseEntity<String> testWebSocket() {
        return ResponseEntity.ok("WebSocket connection test successful!");
    }
}
