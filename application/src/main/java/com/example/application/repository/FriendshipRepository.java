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
    List<Friendship> findByUser1OrUser2(User user1, User user2);

    Optional<Friendship> findByUser1AndUser2(User user1, User user2);

    List<Friendship> findByUser1AndStatus(User user1, FriendshipStatus status);
    List<Friendship> findByUser2AndStatus(User user2, FriendshipStatus status);
}
