package com.example.notes.controller;

import com.example.notes.dto.UserDTO;
import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.TokenRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ResetPasswordController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordResetService passwordResetService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping ("/forget_password")
    public ResponseEntity<?> forgetPasswordProcess(@RequestBody UserEntity userEntity){
        UserEntity user=userRepository.findByEmail(userEntity.getEmail());
        if (user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email Not found");
        }
        String resultOfSending=passwordResetService.sendEmail(user);
        if (resultOfSending.equals("Success")){
            return ResponseEntity.status(HttpStatus.OK).body("The code was sent to your email, Check your email: "+user.getEmail());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error while sending code tou your email: "+user.getEmail());

    }
    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<?> resetPasswordForm(@PathVariable String token, @RequestBody UserDTO userDTO){

        PasswordResetToken resetToken=tokenRepository.findByToken(token);
        if (token==null || resetToken.isUsed()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The code is invalid or is used before");
        }
        if (passwordResetService.hasExpired(resetToken.getExpiryDateTime())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The code is expired, try again");
        }
        if (!(resetToken.getUser().getEmail().equals(userDTO.getEmail()))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect Email");
        }

        UserEntity user= resetToken.getUser();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        resetToken.setUsed(true);
        userRepository.save(user);
        tokenRepository.save(resetToken);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated password successfully");
    }
}
