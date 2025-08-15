package com.example.notesapp.user.controller;


import com.example.notesapp.security.authorization.AuthorizationUtils;
import com.example.notesapp.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(path = "/api/admin",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@RestController
public class AdminController {

    private final UserService userService;
    private final AuthorizationUtils authorizationUtils;

    public AdminController(AuthorizationUtils authorizationUtils,UserService userService) {
        this.authorizationUtils = authorizationUtils;
        this.userService=userService;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name="id") int userId){
        if (authorizationUtils.canCurrentDeleteUser()) {
            userService.removeUser(userId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "User deleted successfully", "userId", userId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error","You are not allowed to delete the user"));

    }
}
