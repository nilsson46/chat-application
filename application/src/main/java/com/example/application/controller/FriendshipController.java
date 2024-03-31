package com.example.application.controller;

import com.example.application.model.Friendship;
import com.example.application.model.User;
import com.example.application.service.AuthenticationService;
import com.example.application.service.FriendshipService;
import com.example.application.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    private final AuthenticationService authenticationService;
    private final FriendshipService friendshipService;
    private final SearchService searchService;

    public FriendshipController(AuthenticationService authenticationService, FriendshipService friendshipService, SearchService searchService) {
        this.authenticationService = authenticationService;
        this.friendshipService = friendshipService;
        this.searchService = searchService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> sendFriendRequest(@RequestParam String otherUsername){
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if (loggedInUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        friendshipService.addFriend(loggedInUsername, otherUsername);
        return ResponseEntity.ok("Friend request sent successfully");
    }
    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam String otherUsername){
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if(loggedInUsername == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        friendshipService.acceptFriendRequest(otherUsername, loggedInUsername);
        return ResponseEntity.ok("Friend request accepted successfully");
    }

    @PostMapping("/decline")
    public ResponseEntity<?> declineFriendRequest(@RequestParam String otherUsername){
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if(loggedInUsername == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        friendshipService.declineFriendRequest(otherUsername, loggedInUsername);
        return ResponseEntity.ok("Friend request declined successfully");
    }

    @GetMapping("/friends")
    public ResponseEntity<?> getFriends() {
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if (loggedInUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        List<String> currentFriends = friendshipService.getCurrentFriendsUsernames(loggedInUsername);
        return ResponseEntity.ok(currentFriends);
    }
    @GetMapping("/friendRequests")
    public ResponseEntity<?> getFriendRequests() {
        String loggedInUsername = authenticationService.getLoggedInUsername();
        if (loggedInUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        List<String> currentFriendRequests = friendshipService.getFriendRequests(loggedInUsername);
        return ResponseEntity.ok(currentFriendRequests);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam String keyword){
        List<User> users = searchService.searchUsers(keyword);
        return ResponseEntity.ok(users);
    }
}
