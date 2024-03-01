package com.example.application.repository;

import com.example.application.model.Friendship;
import com.example.application.model.FriendshipStatus;
import com.example.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findBySenderOrReceiver(User sender, User receiver);

    Optional<Friendship> findBySenderAndReceiver(User sender, User receiver);

    List<Friendship> findBySenderAndStatus(User user1, FriendshipStatus status);
    List<Friendship> findByReceiverAndStatus(User user2, FriendshipStatus status);
}
