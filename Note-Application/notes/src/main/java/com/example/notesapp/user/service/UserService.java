package com.example.notesapp.user.service;


import com.example.notesapp.notes.dto.NoteDTO;
import com.example.notesapp.user.dto.UserDTO;
import com.example.notesapp.notes.models.entities.NoteEntity;
import com.example.notesapp.exception.UserAlreadyFoundException;
import com.example.notesapp.exception.UserNotFoundException;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.notes.repository.NoteRepository;
import com.example.notesapp.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NoteRepository noteRepository;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, NoteRepository noteRepository){

        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.noteRepository=noteRepository;
    }

    public List<NoteDTO> myNote() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        List<NoteEntity> notes = noteRepository.findByUserEntity_Userid(user.get().getUserid());
        if (notes.isEmpty()) {
            return Arrays.asList();
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

    public void register(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyFoundException();
        } else {
            log.info("The user name is: {}", userDTO.getName());
            log.info("The user email is: {}", userDTO.getEmail());

            UserEntity newUser=new UserEntity();

            newUser.setName(userDTO.getName());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(newUser);

        }
    }


    public void removeUser(int id){
        Optional<UserEntity> user=userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException(id);
        }
        List<NoteEntity> notes=noteRepository.findByUserEntity(user.get());

        if (!notes.isEmpty()){
            noteRepository.deleteAll(notes);
        }

        userRepository.delete(user.get());
    }
}



