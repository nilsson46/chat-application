package com.example.application.exception;

public class PasswordMissingException extends RuntimeException{
    public PasswordMissingException (String message){
        super(message);
    }
}
