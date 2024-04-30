package com.example.application.config;

import com.example.application.model.ChatMessage;
import com.example.application.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    //TODO -- add more event listeners

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String sessionId = headerAccessor.getSessionId();

        // Skicka meddelande till alla klienter i samma session
        var chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVE)
                .sender("Server")
                .content("En användare har lämnat chatten")
                .build();
        messageTemplate.convertAndSend("/topic/" + sessionId, chatMessage);
    }
}

