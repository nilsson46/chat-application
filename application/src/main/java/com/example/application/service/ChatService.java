package com.example.application.service;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.Chat;
import com.example.application.model.User;
import com.example.application.repository.ChatRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    public Chat findChatBetweenUsers(String senderUsername, String receiverUsername) {
        // Fetch the sender and receiver User entities from the database
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        // Find a chat between the sender and receiver
        List<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            if (chat.getUsers().contains(sender) && chat.getUsers().contains(receiver)) {
                return chat;
            }
        }

        // If no chat is found, return null
        return null;
    }
    @Transactional
    public Chat createChat(String senderUsername, String receiverUsername) {
        // If the receiver is "public", handle the public chat case
        if ("public".equals(receiverUsername)) {
            // Try to find the special User entity for the public chat
            Optional<User> publicUser = userRepository.findByUsername("public");

            if (publicUser.isPresent()) {
                // The public User entity exists, find the associated Chat entity
                List<Chat> chats = chatRepository.findAll();
                for (Chat chat : chats) {
                    if (chat.getUsers().contains(publicUser.get())) {
                        return chat;
                    }
                }
            } else {
                // The public User entity doesn't exist, create it and a new Chat entity
                User newPublicUser = new User();
                newPublicUser.setUsername("public");
                userRepository.save(newPublicUser);

                Chat publicChat = new Chat();
                publicChat.setUsers(new ArrayList<>());
                publicChat.getUsers().add(newPublicUser);
                return chatRepository.save(publicChat);
            }
        }

        // Fetch the sender and receiver User entities from the database
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        // Check if a chat already exists between the sender and receiver
        List<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            if (chat.getUsers().contains(sender) && chat.getUsers().contains(receiver)) {
                return chat;
            }
        }

        // If no chat exists, create a new Chat entity
        Chat chat = new Chat();
        chat.setUsers(new ArrayList<>()); // Initialize the users list
        chat.getUsers().add(sender);
        chat.getUsers().add(receiver);

        // Save the new Chat entity in the database
        return chatRepository.save(chat);
    }


    @Transactional
    public Chat createPublicChat(String senderUsername) {
        // Try to find the special User entity for the public chat
        Optional<User> publicUser = userRepository.findByUsername("public");

        if (publicUser.isPresent()) {
            // The public User entity exists, find the associated Chat entity
            List<Chat> chats = chatRepository.findAll();
            for (Chat chat : chats) {
                if (chat.getUsers().contains(publicUser.get())) {
                    return chat;
                }
            }
        }

        // If the public chat does not exist, create it
        User newPublicUser = new User();
        newPublicUser.setUsername("public");
        userRepository.save(newPublicUser);

        Chat publicChat = new Chat();
        publicChat.setUsers(new ArrayList<>());
        publicChat.getUsers().add(newPublicUser);
        return chatRepository.save(publicChat);
    }
}