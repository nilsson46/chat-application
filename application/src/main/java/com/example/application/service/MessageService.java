package com.example.application.service;

import com.example.application.model.ChatMessage;
import com.example.application.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final ChatMessageRepository chatMessageRepository;
    public MessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    private List<ChatMessage> messages = new ArrayList<>();

    public ChatMessage createMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getListOfMessages() {
        return messages;
    }
    public List<ChatMessage> getMessages() {
        return chatMessageRepository.findAll();
    }
}
