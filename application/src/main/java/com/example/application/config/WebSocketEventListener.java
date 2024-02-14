package com.example.application.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    //TODO -- add more event listeners
    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent sessionDisconnectEvent

    ){
        //TODO -- to be implemented.
    }
}
