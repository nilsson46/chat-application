package com.example.application.service;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.repository.GroupRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public void createGroup(String loggedInUsername, String groupName, boolean privateGroup) {
    User loggedInUser = userRepository.findByUsername(loggedInUsername).
            orElseThrow(() -> new UserNotFoundException("User with username " + loggedInUsername + "not found"));
        Group group = new Group();
        group.setGroupName(groupName);
        group.setGroupOwner(loggedInUser);
        group.setPrivateGroup(privateGroup);

        groupRepository.save(group);

    }

    public void deleteGroup() {
        // delete group
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
