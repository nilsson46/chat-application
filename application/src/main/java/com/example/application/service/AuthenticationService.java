package com.example.application.service;

import com.example.application.exception.*;
import com.example.application.model.AuthenticationResponse;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthenticationResponse register(User request) {
        validateUserCredentials(request.getUsername(), request.getPassword());
        if (userAlreadyExists(request.getUsername())) {
            throw new UserAlreadyExistsException("User already exists.");
        }
        emailChecker(request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setAge(request.getAge());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setZip(request.getZip());


        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        validateUserCredentials(request.getUsername(), request.getPassword());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid inputs"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    private boolean userAlreadyExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private void validateUserCredentials(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new InvalidInputException("Username is missing");
        }
        if (password == null || password.isEmpty()) {
            throw new InvalidInputException("Password is missing");
        }
    }


    private void emailChecker(String email){
        if(email == null || email.isEmpty()){
            throw new InvalidInputException("Email is missing");
        }
    }
    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
}
