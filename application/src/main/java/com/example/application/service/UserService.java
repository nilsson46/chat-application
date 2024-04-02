package com.example.application.service;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());

        userRepository.save(existingUser);

    }
}
