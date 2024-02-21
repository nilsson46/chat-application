package com.example.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token){
        this.token = token;
    }

}
