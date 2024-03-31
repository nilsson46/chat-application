package com.example.application.controller;

import com.example.application.model.Group;
import com.example.application.service.AuthenticationService;
import com.example.application.service.GroupService;
import com.example.application.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;

    private final SearchService searchService;

    public GroupController(AuthenticationService authenticationService, GroupService groupService, SearchService searchService) {
        this.authenticationService = authenticationService;
        this.groupService = groupService;
        this.searchService = searchService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group){
        groupService.createGroup(authenticationService.getLoggedInUsername(), group.getGroupName(), group.isPrivateGroup());
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
    //Only in private groups
    @PostMapping("/addMember")
    public void addMember(){
        // add member to group
    }
    //Admin only
    @DeleteMapping("/removeMember")
    public void removeMember(){
        // remove member from group
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
        groupService.joinGroup(authenticationService.getLoggedInUsername(), group.getGroupName());
        return ResponseEntity.ok(authenticationService.getLoggedInUsername() +" joined group "+ group.getGroupName());
    }

    @DeleteMapping("/leave")
    public ResponseEntity<?> leaveGroup(@RequestBody Group group){
        groupService.leaveGroup(authenticationService.getLoggedInUsername(), group.getGroupName());
        return ResponseEntity.ok(authenticationService.getLoggedInUsername() +" left group "+ group.getGroupName());
    }

    @GetMapping("/members")
    public void getMembers(){
        // get members of group
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


