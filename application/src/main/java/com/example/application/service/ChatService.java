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

        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));


        List<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            if (chat.getUsers().contains(sender) && chat.getUsers().contains(receiver)) {
                return chat;
            }
        }


        return null;
    }
    @Transactional
    public Chat createChat(String senderUsername, String receiverUsername) {

        if ("public".equals(receiverUsername)) {

            Optional<User> publicUser = userRepository.findByUsername("public");

            if (publicUser.isPresent()) {

                List<Chat> chats = chatRepository.findAll();
                for (Chat chat : chats) {
                    if (chat.getUsers().contains(publicUser.get())) {
                        return chat;
                    }
                }
            } else {

                User newPublicUser = new User();
                newPublicUser.setUsername("public");
                userRepository.save(newPublicUser);

                Chat publicChat = new Chat();
                publicChat.setUsers(new ArrayList<>());
                publicChat.getUsers().add(newPublicUser);
                return chatRepository.save(publicChat);
            }
        }

        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));


        List<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            if (chat.getUsers().contains(sender) && chat.getUsers().contains(receiver)) {
                return chat;
            }
        }


        Chat chat = new Chat();
        chat.setUsers(new ArrayList<>());
        chat.getUsers().add(sender);
        chat.getUsers().add(receiver);


        return chatRepository.save(chat);
    }


    @Transactional
    public Chat createPublicChat(String senderUsername) {

        Optional<User> publicUser = userRepository.findByUsername("public");

        if (publicUser.isPresent()) {

            List<Chat> chats = chatRepository.findAll();
            for (Chat chat : chats) {
                if (chat.getUsers().contains(publicUser.get())) {
                    return chat;
                }
            }
        }


        User newPublicUser = new User();
        newPublicUser.setUsername("public");
        userRepository.save(newPublicUser);

        Chat publicChat = new Chat();
        publicChat.setUsers(new ArrayList<>());
        publicChat.getUsers().add(newPublicUser);
        return chatRepository.save(publicChat);
    }
}