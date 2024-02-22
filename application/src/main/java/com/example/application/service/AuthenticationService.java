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

    public AuthenticationResponse register(User request){
        if(request.getUsername() == null || request.getUsername().isEmpty()){
            throw new UsernameMissingException("Username is missing");
        }
        if(request.getPassword() == null || request.getPassword().isEmpty()){
            throw new PasswordMissingException("Password is missing");
        }
        if(userAlreadyExists(request.getUsername())){
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

    public AuthenticationResponse authenticate (User request) {
        if(request.getUsername() == null || request.getUsername().isEmpty()){
            throw new UsernameMissingException("Username is missing");
        }
        if(request.getPassword() == null || request.getPassword().isEmpty()){
            throw new PasswordMissingException("Password is missing");
        }
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        /*try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new TokenInvalidException("Invalid or expired token");
        }    */
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public boolean userAlreadyExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
