package com.example.notesapp.user.service;


import com.example.notesapp.exception.UserNotFoundException;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.user.models.UserPrinciple;
import com.example.notesapp.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user=userRepository.findByEmail(email);
        log.info("The user information:{} ",user.get().getRoles().getRole_name());
        if (user.isEmpty()){
            throw new UserNotFoundException(user.get().getUserid());
        }
        return new UserPrinciple(user.get());
    }
}
