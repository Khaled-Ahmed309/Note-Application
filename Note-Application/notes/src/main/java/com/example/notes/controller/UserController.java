package com.example.notes.controller;


import com.example.notes.model.UserEntity;
import com.example.notes.response.Response;
import com.example.notes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

    @Autowired
    UserService userService;

    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public ResponseEntity<Response> createUser(@RequestBody  @Valid UserEntity user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.register(user);

    }
}
