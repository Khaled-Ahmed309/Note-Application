package com.example.notes.security;


import com.example.notes.model.Roles;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            return new UsernamePasswordAuthenticationToken(user,null,getAuthorities(user.getRoles()));
        }
            throw  new BadCredentialsException("Invalid credentials");


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
