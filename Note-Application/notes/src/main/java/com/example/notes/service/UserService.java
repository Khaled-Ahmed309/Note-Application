package com.example.notes.service;


import com.example.notes.dto.NoteDTO;
import com.example.notes.dto.UserDTO;
import com.example.notes.model.NoteEntity;
import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.TokenRepository;
import com.example.notes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NoteRepository noteRepository;
    private final TokenRepository tokenRepository;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, NoteRepository noteRepository, TokenRepository tokenRepository){

        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.noteRepository=noteRepository;
        this.tokenRepository=tokenRepository;
    }

    public List<NoteDTO> myNote() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        UserEntity user = userRepository.findByEmail(userEmail);
        List<NoteEntity> notes = noteRepository.findByUserEntity_UserId(user.getUserId());

        if (notes.isEmpty()) {
            throw new RuntimeException("There are no notes. Please add a note to see it here.");
        }
        List<NoteDTO> noteDTOs=new ArrayList<>();

        for (NoteEntity note:notes){
            NoteDTO noteDTO=new NoteDTO();
            noteDTO.setContent(note.getContent());
            noteDTO.setTitle(note.getTitle());
            noteDTOs.add(noteDTO);
        }
        return noteDTOs;

    }


    public void register(UserDTO userDTO){
        try {
            UserEntity user = new UserEntity();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);

        }catch (Exception e){
            throw new RuntimeException("Error while your registration");
        }
    }



    public void changeProfile( UserDTO user) {
        try {
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            String currentEmailUser = auth.getName();

            UserEntity user1 = userRepository.findByEmail(currentEmailUser);
            log.info("User name is: {}", user1.getName());
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
    public void removeUser(int id){
        UserEntity user=userRepository.findByUserId(id);
        List<PasswordResetToken> tokens=tokenRepository.findByUser(user);
        List<NoteEntity> notes=noteRepository.findByUserEntity(user);
        if (user!=null){
            if (!notes.isEmpty()) {
                for (NoteEntity note:notes){
                    note.setUserEntity(null);
                    noteRepository.delete(note);
                }
            }
            if (!tokens.isEmpty()){
               for (PasswordResetToken token:tokens){
                   token.setUser(null);
                   tokenRepository.delete(token);
               }
            }
            userRepository.delete(user);
        }else {
            throw new RuntimeException("Error, user is not found");
        }
    }


}
