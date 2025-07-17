package com.example.notes.controller;


import com.example.notes.dto.UserDTO;

import com.example.notes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/users",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

   private final UserService userService;
   public UserController(UserService userService) {
       this.userService = userService;
   }

   @PostMapping("/register")
   public ResponseEntity<?> createUser(@RequestBody  @Valid UserDTO userDTO){
       userService.register(userDTO);
       Map<String, String> response=new HashMap<>();
       response.put("Message","Registration successful.");
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }


}
