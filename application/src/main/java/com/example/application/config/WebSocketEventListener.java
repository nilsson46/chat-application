package com.example.application.config;

import com.example.application.model.ChatMessage;
import com.example.application.model.MessageType;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;
    private final UserRepository userRepository;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String username = headerAccessor.getUser().getName();
        Optional<User> user = userRepository.findByUsername(username);
        log.warn("Användare ansluten: {}", username);
        if (user.isPresent()) {
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(user.get().getUsername())
                    .content("En användare har lämnat chatten")
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}