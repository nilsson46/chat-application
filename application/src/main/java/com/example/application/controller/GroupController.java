package com.example.application.controller;

import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.service.AuthenticationService;
import com.example.application.service.GroupRequestService;
import com.example.application.service.GroupService;
import com.example.application.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupRequestService groupRequestService;

    private final SearchService searchService;

    public GroupController(AuthenticationService authenticationService, GroupService groupService, GroupRequestService groupRequestService, SearchService searchService) {
        this.authenticationService = authenticationService;
        this.groupService = groupService;
        this.groupRequestService = groupRequestService;
        this.searchService = searchService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group){
        logger.info("Group received in request: {}", group);
        groupService.createGroup(authenticationService.getLoggedInUsername(), group.getGroupName(), group.isPrivate());
        return ResponseEntity.ok("Group created successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteGroup(@RequestBody Group groupName){
        groupService.deleteGroup(authenticationService.getLoggedInUsername(), groupName.getGroupName());
        return ResponseEntity.ok("Group deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchGroups(@RequestParam String keyword){
        List<String> groups = searchService.searchGroupName(keyword);
        return ResponseEntity.ok(groups);
    }

    //Admin only
    @DeleteMapping("/removeMember")
    public ResponseEntity<?> removeMember(@RequestBody Map<String, String> body){
        String groupName = body.get("groupName");
        String username = body.get("username");
        groupService.removeMember(groupName, username);
        return ResponseEntity.ok("Member removed successfully");
    }

    //Owner only or admin maybe?
    @PostMapping("/addAdmin")
    public void addAdmin(){
        // add admin to group
    }
    //Owner only or admin maybe?
    @DeleteMapping("/removeAdmin")
    public void removeAdmin(){
        // remove admin from group
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinGroup(@RequestBody Group group){
        String username = authenticationService.getLoggedInUsername();
        String groupName = group.getGroupName();
        Group targetGroup = groupService.findGroupByName(groupName);
        if (targetGroup.isPrivate()) {
            groupRequestService.createRequest(username, groupName);
            return ResponseEntity.ok("Join request sent successfully");
        } else {
            groupService.joinGroup(username, groupName);
            return ResponseEntity.ok(username +" joined group "+ groupName);
        }
    }

    @DeleteMapping("/leave")
    public ResponseEntity<?> leaveGroup(@RequestBody Group group){
        groupService.leaveGroup(authenticationService.getLoggedInUsername(), group.getGroupName());
        return ResponseEntity.ok(authenticationService.getLoggedInUsername() +" left group "+ group.getGroupName());
    }

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@RequestBody Group group){
        return ResponseEntity.ok(groupService.getMembersUsername(group.getGroupName()));
    }

    @GetMapping("/admins")
    public void getAdmins(){
        // get admins of group
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getAllgroups(){
    return ResponseEntity.ok(groupService.getAllGroups());
    }
}


