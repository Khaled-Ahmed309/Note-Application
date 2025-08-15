package com.example.notesapp.user.controller;


import com.example.notesapp.user.dto.AuthRequest;
import com.example.notesapp.user.dto.AuthResponse;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.user.service.JwtUtilService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtilService jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest authRequest, AuthResponse authResponse){
        Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        UserEntity user= (UserEntity) auth.getPrincipal();
        String token =jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

}
