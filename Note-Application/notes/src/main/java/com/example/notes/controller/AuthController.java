package com.example.notes.controller;


import com.example.notes.dto.AuthRequest;
import com.example.notes.dto.AuthResponse;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest authRequest, AuthResponse authResponse){
        Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        UserEntity user= (UserEntity) auth.getPrincipal();
        String token =jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

}
