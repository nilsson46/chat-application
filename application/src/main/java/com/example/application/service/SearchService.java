package com.example.application.service;

import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.repository.GroupRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<User> searchUsers(String keyword) {
        // Söker efter användare med användarnamn som innehåller det angivna sökordet
        return userRepository.findByUsernameContaining(keyword);
    }

    public List<Group> searchGroups(String keyword) {
        // Söker efter grupper med gruppnamn som innehåller det angivna sökordet
        return groupRepository.findByGroupNameContaining(keyword);
    }
}
