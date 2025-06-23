package com.example.notes.controller;


import com.example.notes.config.AuthorizationUtils;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.UserEntity;
import com.example.notes.response.Response;
import com.example.notes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

   private final UserService userService;
   public UserController(UserService userService) {
       this.userService = userService;
   }

   @PostMapping("/register")
   public ResponseEntity<?> createUser(@RequestBody  @Valid UserDTO userDTO){
       userService.register(userDTO);
       return ResponseEntity.status(HttpStatus.ACCEPTED).body("Your registration is successfully");
   }


}
