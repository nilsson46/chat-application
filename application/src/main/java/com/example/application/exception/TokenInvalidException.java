package com.example.application.exception;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException (String message){
        super(message);
    }
}
