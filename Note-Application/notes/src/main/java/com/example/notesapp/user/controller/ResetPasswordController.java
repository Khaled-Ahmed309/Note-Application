package com.example.notesapp.user.controller;


import com.example.notesapp.user.dto.PasswordDTO;
import com.example.notesapp.user.dto.UserDTO;
import com.example.notesapp.user.service.PasswordResetService;
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
    public ResponseEntity<?> processForgotPassword(@RequestBody @Valid UserDTO userDTO, HttpSession session) {
        if (passwordResetService.isUserFound(userDTO,session)) {
            passwordResetService.saveTokenInDB(userDTO);
            passwordResetService.sendEmail(userDTO);
            return ResponseEntity.ok("A reset code was sent to your email: " + userDTO.getEmail());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> verifyResetToken(@PathVariable(name = "token") String token,
                                              @Valid @RequestBody PasswordDTO passwordDTO, HttpSession session) {
        if (passwordResetService.verifyToken(token,session,passwordDTO)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password updated successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
    }
}
