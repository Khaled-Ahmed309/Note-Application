package com.example.notes.controller;


import com.example.notes.dto.LoginRequest;
import com.example.notes.dto.LoginResponse;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class LoginController {
    //immutable
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtil;

    public LoginController(JwtUtilService jwtUtil, AuthenticationManager authenticationManager){
        this.jwtUtil=jwtUtil;
        this.authenticationManager = authenticationManager;
    }




    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest){
        Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        UserEntity user= (UserEntity) auth.getPrincipal();
        String token =jwtUtil.generateToken(user);
        LoginResponse loginResponse=new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }


}
