package com.example.notesapp.security.authorization;

import com.example.notesapp.notes.models.entities.NoteEntity;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.notes.repository.NoteRepository;
import com.example.notesapp.user.repository.UserRepository;
import com.example.notesapp.user.service.MyUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorizationUtils {


    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public AuthorizationUtils(UserRepository userRepository,NoteRepository noteRepository,MyUserDetailsService myUserDetailsService){
        this.userRepository=userRepository;
        this.noteRepository=noteRepository;
    }
    public boolean canCurrentUserAccessNote(int  noteId){
        Optional<NoteEntity> note =noteRepository.findById(noteId);
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String loggedUserEmail=auth.getName();
        Optional<UserEntity> currentUser=userRepository.findByEmail(loggedUserEmail);

        if (currentUser.isEmpty()||note.isEmpty()||note.get().getUserEntity()==null){
            return false;
        }

        int currentUserId=currentUser.get().getUserid();
        int ownerId=note.get().getUserEntity().getUserid();

/*
        String currentUserRole=authentication.getAuthorities().toString();
         who is a role the user is USER OR ADMIN
*/

        boolean isOwner=currentUserId==ownerId&&note.get().getNoteId()>0;
        boolean isAdmin=currentUser.get().getRoles().getRole_name().equals("ADMIN");

        return isOwner||isAdmin;
    }
    public boolean canCurrentDeleteUser(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        String roleUser=userRepository.findByEmail(email).get().getRoles().getRole_name();
        return roleUser.equals("ADMIN");
    }

}
