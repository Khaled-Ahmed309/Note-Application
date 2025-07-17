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
@RequestMapping(path = "/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ResetPasswordController {

    private final PasswordResetService passwordResetService;

    public ResetPasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> processForgotPassword(@RequestBody UserDTO userDTO, HttpSession session) {
        if (passwordResetService.isUserFound(userDTO, session)) {
            passwordResetService.saveTokenInDB(session);
            passwordResetService.sendEmail(session);
            return ResponseEntity.ok("A reset code was sent to your email: " + userDTO.getEmail());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
    }

    @PostMapping("/verify-reset-token/{token}")
    public ResponseEntity<?> verifyResetToken(@PathVariable String token, HttpSession session) {
        if (passwordResetService.verifyToken(token, session)) {
            return ResponseEntity.ok("Token is valid");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("userInformation");
        PasswordResetToken resetToken = (PasswordResetToken) session.getAttribute("TokenInformation");

        if (user != null && resetToken != null &&
                resetToken.getToken() != null && !resetToken.isUsed() &&
                resetToken.getUser().getEmail().equals(user.getEmail()) &&
                passwordResetService.NotExpired(resetToken.getExpiryDateTime())) {

            passwordResetService.saveNewPassword(passwordDTO, session);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password updated successfully");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reset password conditions not met");
    }
}
