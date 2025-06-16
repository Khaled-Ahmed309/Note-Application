package com.example.notes.controller;


import com.example.notes.dto.UserDTO;
import com.example.notes.model.UserEntity;
import com.example.notes.service.MyUserDetailsService;
import com.example.notes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/update",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
public class UserUpdateController {


    @Autowired
    UserService userService;


    @PostMapping("/updateMyProfile")
    public ResponseEntity<?> updateMyProfile(@RequestBody @Valid UserDTO user, Authentication authentication){
        userService.changeProfile(user,authentication);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated profile successfully");
    }

}
