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

    public void addFriend(String loggedInUsername, String otherUserName) {

        User loggedInUser = userRepository.findByUsername(loggedInUsername).
                orElseThrow(() -> new UserNotFoundException("User with username " + loggedInUsername + "not found"));
        User otherUser = userRepository.findByUsername(otherUserName).
                orElseThrow(() -> new UserNotFoundException("User with username " + otherUserName + "not found"));

        Friendship friendship = new Friendship();
        friendship.setSender(loggedInUser);
        friendship.setReceiver(otherUser);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);

    }

    public List<Friendship> getFriendshipsList(User user) {
        return friendshipRepository.findBySenderOrReceiver(user, user);
    }

    public void acceptFriendRequest(String otherUsername, String loggedInUsername) {

        // Hämta användarobjekt för både den inloggade användaren och den andra användaren
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new UserNotFoundException("Logged in user not found"));
        User otherUser = userRepository.findByUsername(otherUsername)
                .orElseThrow(() -> new UserNotFoundException("Other user not found"));

        // Hämta Friendship-objektet baserat på både avsändaren och mottagaren av vänförfrågan
        Friendship friendship = friendshipRepository.findBySenderAndReceiver(otherUser, loggedInUser)
                .orElseThrow(() -> new UserNotFoundException("Friendship not found"));

        // Kontrollera om vänförfrågan är i statusen "PENDING" och om den inloggade användaren är mottagaren av vänförfrågan
        if (friendship.getStatus() == FriendshipStatus.PENDING && friendship.getReceiver().equals(loggedInUser)) {
            // Uppdatera vänskapsförfrågningsstatusen till "accepted" eller liknande
            friendship.setStatus(FriendshipStatus.ACCEPTED);
            friendshipRepository.save(friendship);
        } else {
            // Om förfrågan inte är giltig, kasta ett undantag eller hantera på annat sätt
            throw new IllegalStateException("Invalid friendship request");
        }
    }

    /*public List<String> getFriends(User user){
        List<Friendship> friendships = friendshipRepository.findByUser1AndStatus(user,)
    } */
}

