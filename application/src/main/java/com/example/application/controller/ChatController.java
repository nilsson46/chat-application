package com.example.application.controller;

import com.example.application.model.Chat;
import com.example.application.model.ChatMessage;
import com.example.application.model.MessageType;
import com.example.application.repository.ChatMessageRepository;
import com.example.application.repository.UserRepository;
import com.example.application.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatService chatService;

    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessageViaSocket(@Payload ChatMessage chatMessage) {
        log.info("Received message: " + chatMessage.getContent());
        try {

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
            log.info("Message sent to topic /topic/public");
            chatMessage.setType(MessageType.PUBLIC);

            Chat chat = chatService.createPublicChat(chatMessage.getSender());
            chatMessage.setChat(chat);

            chatMessageRepository.save(chatMessage);
            log.info("Message saved to the database");
        } catch (Exception e) {
            log.error("Error while sending message: " + e.getMessage());
        }
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    @MessageMapping("/chat/sendMessage/{recipientUsername}")
    public void sendPrivateMessageViaSocket(@Payload ChatMessage chatMessage) {
        log.info("Received private message: " + chatMessage.getContent());
        String recipientUsername = chatMessage.getReceiver();
        System.out.println("Recipient: " + recipientUsername);
        try {

            Chat chat = chatService.createChat(chatMessage.getSender(), recipientUsername);
            chatMessage.setType(MessageType.PRIVATE);

            chatMessage.setChat(chat);

            String topic = "/topic/private/" + recipientUsername;
            messagingTemplate.convertAndSend(topic, chatMessage);
            log.info("Message sent to topic " + topic);
            chatMessageRepository.save(chatMessage);
            log.info("Message saved to the database");
        } catch (Exception e) {
            log.error("Error while sending message to topic /topic/private: " + e.getMessage());
        }
    }
    @SubscribeMapping("/topic/private/{recipientUsername}/{senderUsername}")
    public void subscribeToPrivateTopic(@DestinationVariable String recipientUsername, SimpMessageHeaderAccessor headerAccessor) {
        String currentUsername = getCurrentUsername();
        if (currentUsername.equals(recipientUsername)) {
            System.out.println("Message received for " + currentUsername);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAllByOrderByTimestampAsc();
    }
    @GetMapping("/join-chat")
    public ResponseEntity<String> joinChat() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String responseMessage = "Ansluten till chatten! Välkommen, " + username + "!";
        log.info(responseMessage);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/test-websocket")
    public ResponseEntity<String> testWebSocket() {
        log.info("WebSocket connection test successful!");
        return ResponseEntity.ok("WebSocket connection test successful!");
    }
}
