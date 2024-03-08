package com.example.application.controller;

import com.example.application.model.AuthenticationResponse;
import com.example.application.model.User;
import com.example.application.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User request){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    //TODO add a password check as well?
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
