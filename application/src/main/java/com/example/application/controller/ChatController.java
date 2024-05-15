package com.example.application.controller;

import com.example.application.model.ChatMessage;
import com.example.application.repository.ChatMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/public")
    public void sendMessageViaSocket(@Payload ChatMessage chatMessage) {
        log.info("Received message: " + chatMessage.getContent()); // Lägger till loggning för att skriva ut meddelandet i konsollen
        try {
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
            log.info("Message sent to topic /topic/public");
            chatMessageRepository.save(chatMessage);
            log.info("Message saved to the database");
        } catch (Exception e) {
            log.error("Error while sending message to topic /topic/public: " + e.getMessage());
        }
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    @GetMapping("/api/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAllByOrderByTimestampAsc();
    }
    @GetMapping("/join-chat")
    public ResponseEntity<String> joinChat() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String responseMessage = "Ansluten till chatten! Välkommen, " + username + "!";
        log.info(responseMessage); // Lägger till loggmeddelande när en användare ansluter till chatten
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/test-websocket")
    public ResponseEntity<String> testWebSocket() {
        log.info("WebSocket connection test successful!"); // Lägger till loggmeddelande för att indikera att WebSocket-testet lyckades
        return ResponseEntity.ok("WebSocket connection test successful!");
    }
}
