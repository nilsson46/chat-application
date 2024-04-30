package com.example.application.controller;

import com.example.application.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class WebsocketController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SessionManager sessionManager;

    @MessageMapping("/join/{sessionId}")
    public void joinSession(@DestinationVariable String sessionId, Principal principal) {
        sessionManager.addUserToSession(sessionId, principal.getName());
        // Implementera logik för att skicka bekräftelse eller meddelanden till användaren vid anslutning
    }

    @MessageMapping("/leave/{sessionId}")
    public void leaveSession(@DestinationVariable String sessionId, Principal principal) {
        sessionManager.removeUserFromSession(sessionId, principal.getName());
        // Implementera logik för att skicka bekräftelse eller meddelanden till användaren vid frånkoppling
    }
    String destination = "/topic/messages";

    ExecutorService executorService =
            Executors.newFixedThreadPool(1);
    Future<?> submittedTask;

    @MessageMapping("/start")
    public void startTask(){
        if ( submittedTask != null ){
            simpMessagingTemplate.convertAndSend(destination,
                    "Task already started");
            return;
        }
        simpMessagingTemplate.convertAndSend(destination,
                "Started task");
        submittedTask = executorService.submit(() -> {
            while(true){
                simpMessagingTemplate.convertAndSend(destination,
                        LocalDateTime.now().toString()
                                +": doing some work");
                Thread.sleep(10000);
            }
        });
    }

    @MessageMapping("/stop")
    @SendTo("/topic/messages")
    public String stopTask(){
        if ( submittedTask == null ){
            return "Task not running";
        }
        try {
            submittedTask.cancel(true);
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error occurred while stopping task due to: "
                    + ex.getMessage();
        }
        return "Stopped task";
    }
}
