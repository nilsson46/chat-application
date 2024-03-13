package com.example.application.controller;

import com.example.application.service.AuthenticationService;
import com.example.application.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final AuthenticationService authenticationService;
    private final GroupService groupService;

    public GroupController(AuthenticationService authenticationService, GroupService groupService) {
        this.authenticationService = authenticationService;
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody String groupName, boolean privateGroup){
        groupService.createGroup(authenticationService.getLoggedInUsername(), groupName, privateGroup);
        return ResponseEntity.ok("Group created successfully");
    }

    @DeleteMapping("/delete")
        public void deleteGroup(){
            // delete group
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
    public void joinGroup(){
        // join group
    }

    @DeleteMapping("/leave")
    public void leaveGroup(){
        // leave group
    }

    @GetMapping("/members")
    public void getMembers(){
        // get members of group
    }

    @GetMapping("/admins")
    public void getAdmins(){
        // get admins of group
    }

}


