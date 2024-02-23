package com.example.application.exception;

public class UsernameMissingException extends RuntimeException{
    public UsernameMissingException(String message){
        super(message);
    }
}
