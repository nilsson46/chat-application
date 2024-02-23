package com.example.application.service;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.Friendship;
import com.example.application.model.FriendshipStatus;
import com.example.application.model.User;
import com.example.application.repository.FriendshipRepository;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    public void addFriend(String loggedInUsername, String otherUserName){

        User loggedInUser = userRepository.findByUsername(loggedInUsername).
                orElseThrow(() -> new UserNotFoundException("User with username " + loggedInUsername + "not found"));
        User otherUser = userRepository.findByUsername(otherUserName).
                orElseThrow(() -> new UserNotFoundException("User with username " + otherUserName + "not found"));

        Friendship friendship = new Friendship();
        friendship.setUser1(loggedInUser);
        friendship.setUser2(otherUser);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);

    }

    public List<Friendship> getFriendshipsList(User user){
        return friendshipRepository.findByUser1OrUser2(user, user);
    }

    public void acceptFriendRequest(String otherUsername, String loggedInUsername) {

        // Hämta användarobjekt för både den inloggade användaren och den andra användaren
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("Logged in user not found"));
        User otherUser = userRepository.findByUsername(otherUsername)
                .orElseThrow(() -> new UserNotFoundException("Other user not found"));

        // Uppdatera vänförfrågningsstatusen eller utför andra åtgärder för att acceptera vänförfrågan
        // I detta exempel antar vi att vi har en Friendship-entitet som innehåller statusen för vänförfrågan
        Friendship friendship = friendshipRepository.findByUser1AndUser2(loggedInUser, otherUser)
                .orElseThrow(() -> new UserNotFoundException("Friendship not found"));
        System.out.println(friendship.getUser1());
        System.out.println(friendship.getUser2());

        // Uppdatera vänförfrågningsstatusen till "accepted" eller liknande
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(friendship);
    }
}
