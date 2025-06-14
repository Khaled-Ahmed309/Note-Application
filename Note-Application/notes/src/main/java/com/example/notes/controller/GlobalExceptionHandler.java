package com.example.notes.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex){

        Map<String,Object> error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("Message","Something went wrong");
        error.put("Details",ex.getMessage());
        error.put("Status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
