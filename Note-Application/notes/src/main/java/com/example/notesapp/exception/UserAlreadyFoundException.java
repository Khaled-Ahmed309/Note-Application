package com.example.notesapp.exception;

public class UserAlreadyFoundException extends RuntimeException {
    public UserAlreadyFoundException() {

        super("User is already found, try with another user");
    }
}
