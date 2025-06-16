package com.example.notes.controller;

import com.example.notes.dto.PasswordDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.TokenRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.PasswordResetService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/forget_password")
    public ResponseEntity<?> forgetPasswordProcess(@RequestBody UserDTO userDTO, HttpSession httpSession) {
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        httpSession.setAttribute("userInformation", user);
        boolean isUserFound = passwordResetService.verifyEmail(httpSession);
        if (isUserFound) {
            passwordResetService.saveTokenInDB(httpSession);
            passwordResetService.sendEmail(httpSession);
            return ResponseEntity.status(HttpStatus.OK).body("The code was sent to your email, Check your email: " + userDTO.getEmail());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email Not found");
    }

    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<?> resetPasswordForm(@PathVariable String token, HttpSession httpSession) {

        if (passwordResetService.verifyToken(token, httpSession)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The code is correct and valid");
        }
     /*   PasswordResetToken resetToken=tokenRepository.findByToken(token);
        if (token==null || resetToken.isUsed()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The code is invalid or is used before");
        }
        if (passwordResetService.hasExpired(resetToken.getExpiryDateTime())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The code is expired, try again");
        }
        if (!(resetToken.getUser().getEmail().equals(userDTO.getEmail()))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect Email");*/
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The code is incorrect or invalid");

    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid PasswordDTO passwordDTO, HttpSession httpSession) {
        UserEntity user = (UserEntity) httpSession.getAttribute("userInformation");
        PasswordResetToken resetToken = (PasswordResetToken) httpSession.getAttribute("TokenInformation");
        if (user != null &&
                resetToken.getToken() != null && !(resetToken.isUsed()) &&
                resetToken.getUser().getEmail().equals(user.getEmail()) &&
                passwordResetService.NotExpired(resetToken.getExpiryDateTime())) {
            user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
            userRepository.save(user);
            resetToken.setUsed(true);
            tokenRepository.save(resetToken);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated password accepted");
        } else {
            throw new RuntimeException("Error is found");
        }


    }
}
