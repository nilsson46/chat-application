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
        List<User> users = userRepository.findByUsernameContaining(keyword);

        List<String> usernames = new ArrayList<>();

        for (User user : users) {
            usernames.add(user.getUsername());
        }

        return usernames;
    }

    public List<String> searchGroupName(String keyword) {

        List<Group> groups = groupRepository.findByGroupNameContaining(keyword);


        List<String> groupArray = new ArrayList<>();


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
