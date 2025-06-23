package com.example.notes.controller;


import com.example.notes.config.AuthorizationUtils;
import com.example.notes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/admin",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@RestController
public class AdminController {

    private final UserService userService;
    private final AuthorizationUtils authorizationUtils;

    public AdminController(AuthorizationUtils authorizationUtils,UserService userService) {
        this.authorizationUtils = authorizationUtils;
        this.userService=userService;
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name="id") int userId){
        if (authorizationUtils.canCurrentDeleteUser()) {
            userService.removeUser(userId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Removed user id: "+userId+" successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your are not allow to delete the user");

    }
}
