package com.example.application.service;

import com.example.application.model.Group;
import com.example.application.model.GroupRequest;
import com.example.application.model.User;
import com.example.application.repository.GroupRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupRequestService {
    private final GroupRequestRepository groupRequestRepository;
    private GroupRequest groupRequest;

    @Autowired
    public GroupRequestService(GroupRequestRepository groupRequestRepository) {
        this.groupRequestRepository = groupRequestRepository;
    }
    //Admin only

    public void approveRequest() {
        // approve request to join group
    }

    //Admin only
    public void rejectRequest(String username, String groupName) {
        // reject request to join group
    }

    //Admin only
    public List<GroupRequest> getPendingRequests(String groupName) {
        return groupRequestRepository.findByGroupName(groupName);
    }

    public void createRequest(String username, String groupName) {
        groupRequest = new GroupRequest();
        groupRequest.setUsername(username);
        groupRequest.setGroupName(groupName);
        groupRequestRepository.save(groupRequest);
    }
}
