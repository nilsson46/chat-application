package com.example.application.service;

import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.repository.GroupRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<String> searchUsername(String keyword) {
        // Hämta alla grupper som innehåller sökordet i gruppnamnet
        List<User> users = userRepository.findByUsernameContaining(keyword);

        // Skapa en lista för att lagra användarnamnen från grupperna
        List<String> usernames = new ArrayList<>();

        // Loopa igenom varje grupp och hämta användarnamnen
        for (User user : users) {
            usernames.add(user.getUsername());
        }

        return usernames;
    }

    public List<String> searchGroupName(String keyword) {
        // Hämta alla grupper som innehåller sökordet i gruppnamnet
        List<Group> groups = groupRepository.findByGroupNameContaining(keyword);

        // Skapa en lista för att lagra användarnamnen från grupperna
        List<String> groupArray = new ArrayList<>();

        // Loopa igenom varje grupp och hämta användarnamnen
        for (Group group : groups) {
            groupArray.add(group.getGroupName());
        }

        return groupArray;
    }
    public List<String> getAllGroups() {

        //TODO Maybe add more info? Amount of members? Owner? Private or public?
        return groupRepository.findAll().stream()
                .map(Group::getGroupName)
                .collect(Collectors.toList());
    }
}
