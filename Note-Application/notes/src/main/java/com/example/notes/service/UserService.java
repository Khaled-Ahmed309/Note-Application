package com.example.notes.service;


import com.example.notes.dto.UserDTO;
import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.model.UserPrinciple;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MyUserDetailsService userDetailsService;

    UserPrinciple userPrinciple;
    public boolean saveUser(UserEntity userEntity){

        if (userEntity!=null){
          userRepository.save(userEntity);
          return true;
        }
        return false;
    }

    //Get the user's notes
    public List<NoteEntity> userNote(int user_id){
        Optional<UserEntity> user=userRepository.findById(user_id);
        if (user.isPresent()){
            return noteRepository.findByUserEntity_UserId(user_id);
        }
        return List.of();
    }

    public ResponseEntity<Response> register(UserEntity user){
        Response response=new Response();
        userRepository.save(user);
        response.setStatusCode("200");
        response.setStatusMsg("The email is saved successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    public void changeProfile( UserDTO user, Authentication authentication) {
        try {
            String currentEmailUser = authentication.getName();
            log.info("User name is: {}", currentEmailUser);

            UserEntity user1 = userRepository.findByEmail(currentEmailUser);
            if (user.getName() != null) {
                user1.setName(user.getName());
            }
            if (user.getEmail() != null) {
                user1.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                user1.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user1);
        }catch (Exception ex){
            log.error("Error while updating profile", ex);
            throw new RuntimeException("Something went wrong while updating the profile");
        }
    }


}
