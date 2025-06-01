package com.example.notes.service;


import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;

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
}
