package com.example.application.service;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public User updateUser(String username, Map<String, Object> updates) throws UserNotFoundException {
        User userToUpdate = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(updates.containsKey("address")) {
            userToUpdate.setAddress(updates.get("address").toString());
        }
        if(updates.containsKey("city")) {
            userToUpdate.setCity(updates.get("city").toString());
        }
        if(updates.containsKey("country")) {
            userToUpdate.setCountry(updates.get("country").toString());
        }
        if(updates.containsKey("zip")) {
            userToUpdate.setZip(Integer.parseInt(updates.get("zip").toString()));
        }
        if(updates.containsKey("age")) {
            int age = Integer.parseInt(updates.get("age").toString());
            if(age < 0){
                throw new IllegalArgumentException("Age cannot be negative");
            }
            userToUpdate.setAge(age);
        }

        userRepository.save(userToUpdate);
        return userToUpdate;
    }
}
