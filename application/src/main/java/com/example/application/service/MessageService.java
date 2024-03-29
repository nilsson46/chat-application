package com.example.application.service;

import com.example.application.model.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private List<ChatMessage> messages = new ArrayList<>();

    public ChatMessage createMessage(ChatMessage message) {
        messages.add(message);
        return message;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }
}
