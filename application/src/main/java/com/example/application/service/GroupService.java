package com.example.application.service;

import com.example.application.exception.UserAlreadyExistsException;
import com.example.application.exception.UserNotFoundException;
import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.repository.GroupRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }
    @Transactional
    public void createGroup(String loggedInUsername, String groupName, boolean privateGroup) {
    User loggedInUser = userRepository.findByUsername(loggedInUsername).
            orElseThrow(() -> new UserNotFoundException("User with username " + loggedInUsername + "not found"));

    if(groupAlreadyExists(groupName)){
      throw new UserAlreadyExistsException("Group with name " + groupName + " already exists");
    }
        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupOwner((loggedInUsername));
        group.setPrivateGroup(privateGroup);

        groupRepository.save(group);

    }

    private boolean groupAlreadyExists(String groupName) {
        return groupRepository.findByGroupName(groupName).isPresent();
    }
    @Transactional
    public void deleteGroup(String loggedInUsername, String groupName) {
        Group group = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new UserNotFoundException("Group with name " + groupName + " not found"));

        if (group.getGroupOwner().equals(loggedInUsername)) {
            groupRepository.delete(group);
        } else {
            throw new UserNotFoundException(
                    "User with username " + loggedInUsername + " is not the owner of the group");
        }
    }
    public void addMember() {
    }

    public void removeMember(String groupName, String username) {
        Group group = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new UserNotFoundException("Group with name " + groupName + " not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        group.getMembers().remove(user);
        groupRepository.save(group);

    }

    public void addAdmin() {
        // add admin to group
    }

    public void removeAdmin() {
        // remove admin from group
    }
    @Transactional
    public void joinGroup(String loggedInUsername, String groupName) {
        Group group = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new UserNotFoundException("Group with name " + groupName + " not found"));
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("User with username " + loggedInUsername + " not found"));
        group.getMembers().add(user);
        groupRepository.save(group);
    }
    @Transactional
    public void leaveGroup(String loggedInUsername, String groupName){
        Group group = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new UserNotFoundException("Group with name " + groupName + " not found"));
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("User with username " + loggedInUsername + " not found"));
        group.getMembers().remove(user);
        groupRepository.save(group);
    }

    public List<String> getAllGroups() {

        //TODO Maybe add more info? Amount of members? Owner? Private or public?
        return groupRepository.findAll().stream()
                .map(Group::getGroupName)
                .collect(Collectors.toList());
    }

    public List<String> getMembersUsername(String groupName) {
        Group group = groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new UserNotFoundException("Group with name " + groupName + " not found"));
        return group.getGroupMembers().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
    public List<Group> searchGroups(String keyword) {
        return groupRepository.findByGroupNameContaining(keyword);
    }
}
