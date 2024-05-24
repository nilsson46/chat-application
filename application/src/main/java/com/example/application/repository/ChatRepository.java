package com.example.application.repository;

import com.example.application.model.Chat;
import com.example.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat save(Chat chat);
}
