package com.example.notes.controller;

import com.example.notes.dto.PasswordDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;

import com.example.notes.service.PasswordResetService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ResetPasswordController {

    private final PasswordResetService passwordResetService;

    public ResetPasswordController(PasswordResetService passwordResetService){
        this.passwordResetService=passwordResetService;
    }


    @PostMapping("/forget_password")
    public ResponseEntity<?> forgetPasswordProcess(@RequestBody UserDTO userDTO, HttpSession httpSession) {

        if (passwordResetService.isUserFound(userDTO,httpSession)) {
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
            passwordResetService.saveNewPassword(passwordDTO,httpSession);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated password accepted");
        } else {
            throw new RuntimeException("Error is found");
        }


    }
}
