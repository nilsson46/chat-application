package com.example.application.service;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.repository.GroupRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupOwner((loggedInUsername));
        group.setPrivateGroup(privateGroup);

        groupRepository.save(group);

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
        // add member to group
    }

    public void removeMember() {
        // remove member from group
    }

    public void addAdmin() {
        // add admin to group
    }

    public void removeAdmin() {
        // remove admin from group
    }

    public void joinGroup() {
        // join group
    }

    public void leaveGroup() {
        // leave group
    }

    public void getMembers() {
        // get members
    }
}
