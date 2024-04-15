package com.example.application.service;

import java.util.List;

public class GroupRequestService {


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
        // get pending requests to join group

    }
}
