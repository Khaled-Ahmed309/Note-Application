package com.example.notes.service;


import com.example.notes.model.UserEntity;
import com.example.notes.model.UserPrinciple;
import com.example.notes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(email);
        log.info("The user information:{} ",user.getRoles().getRole_name());
        if (user==null){
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrinciple(user);
    }
}
