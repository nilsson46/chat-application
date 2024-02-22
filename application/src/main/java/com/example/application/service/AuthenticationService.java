package com.example.application.service;

import com.example.application.exception.*;
import com.example.application.model.AuthenticationResponse;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Metod för att registrera en ny användare
    public AuthenticationResponse register(User request) {
        validateUserCredentials(request.getUsername(), request.getPassword());
        if (userAlreadyExists(request.getUsername())) {
            throw new UserAlreadyExistsException("User already exists.");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    // Metod för att autentisera en användare
    public AuthenticationResponse authenticate(User request) {
        validateUserCredentials(request.getUsername(), request.getPassword());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    // Metod för att kontrollera om en användare redan existerar i databasen
    private boolean userAlreadyExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Metod för att validera användaruppgifter (användarnamn och lösenord)
    private void validateUserCredentials(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new UsernameMissingException("Username is missing");
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordMissingException("Password is missing");
        }
    }
}
