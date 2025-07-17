package com.example.notes.controller;


import com.example.notes.dto.NameDTO;
import com.example.notes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
public class UserUpdateController {


   private final UserService userService;

   public UserUpdateController(UserService userService){
       this.userService=userService;
   }


    @PostMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody @Valid NameDTO nameDTO){
        userService.changeName(nameDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Profile updated successfully.");
    }

}
