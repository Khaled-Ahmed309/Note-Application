package com.example.notesapp.security;


import com.example.notesapp.user.models.entities.Roles;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class NotesApplicationUsernamePassword implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public NotesApplicationUsernamePassword(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email=authentication.getName();
        String pwd=authentication.getCredentials().toString();
        UserEntity user=userRepository.findByEmail(email).
                orElseThrow(()->new BadCredentialsException("Invalid credentials, user not found"));
        if ( passwordEncoder.matches(pwd,user.getPassword()))
        {
            log.info("The user name is: {}",user.getName());
            log.info("The user email is: {}",user.getEmail());
            return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user.getRoles()));
        }
            throw new BadCredentialsException("Invalid credentials, password or username is incorrect");


    }
    public List<GrantedAuthority> getAuthorities(Roles roles){
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRole_name()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
