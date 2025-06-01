package com.example.notes.security;


import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NotesApplicationUsernamePassword implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email=authentication.getName();
        String pwd=authentication.getCredentials().toString();
        UserEntity user=userRepository.findByEmail(email);
        if (user!=null&&user.getUserId()>0&& passwordEncoder.matches(pwd,user.getPassword()))
        {
            return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
        }
            throw  new BadCredentialsException("Invalid cradiatels");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
