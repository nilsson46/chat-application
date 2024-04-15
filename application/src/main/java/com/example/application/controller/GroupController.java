package com.example.application.controller;

import com.example.application.model.Group;
import com.example.application.model.User;
import com.example.application.service.AuthenticationService;
import com.example.application.service.GroupService;
import com.example.application.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        groupService.joinGroup(authenticationService.getLoggedInUsername(), group.getGroupName());
        return ResponseEntity.ok(authenticationService.getLoggedInUsername() +" joined group "+ group.getGroupName());
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


