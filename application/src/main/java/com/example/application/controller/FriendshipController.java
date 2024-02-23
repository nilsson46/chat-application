package com.example.application.controller;

import com.example.application.service.AuthenticationService;
import com.example.application.service.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    private final AuthenticationService authenticationService;
    private final FriendshipService friendshipService;

    public FriendshipController(AuthenticationService authenticationService, FriendshipService friendshipService) {
        this.authenticationService = authenticationService;
        this.friendshipService = friendshipService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> sendFriendRequest(@RequestBody String otherUsername){
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if (loggedInUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        friendshipService.addFriend(loggedInUsername, otherUsername);
        return ResponseEntity.ok("Friend request sent successfully");
    }
    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody String otherUsername){
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if(loggedInUsername == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        friendshipService.acceptFriendRequest(otherUsername, loggedInUsername);
        return ResponseEntity.ok("Friend request accepted successfully");
    }
}
