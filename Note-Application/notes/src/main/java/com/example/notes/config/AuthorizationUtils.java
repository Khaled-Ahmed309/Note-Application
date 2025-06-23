package com.example.notes.config;

import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorizationUtils {


    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private MyUserDetailsService myUserDetailsService;

    public AuthorizationUtils(UserRepository userRepository,NoteRepository noteRepository,MyUserDetailsService myUserDetailsService){
        this.userRepository=userRepository;
        this.noteRepository=noteRepository;
        this.myUserDetailsService=myUserDetailsService;
    }
    public boolean canCurrentUserAccessNote(int  noteId){
        NoteEntity note =noteRepository.findByNoteId(noteId);
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String loggedUserEmail=auth.getName();
        UserEntity currentUser=userRepository.findByEmail(loggedUserEmail);

        if (currentUser==null||note==null||note.getUserEntity()==null){
            return false;
        }

        int currentUserId=currentUser.getUserId();
        int ownerId=note.getUserEntity().getUserId();

/*
        String currentUserRole=authentication.getAuthorities().toString();
         who is a role the user is USER OR ADMIN
*/

        boolean isOwner=currentUserId==ownerId&&note.getNoteId()>0;
        boolean isAdmin=currentUser.getRoles().getRole_name().equals("ADMIN");

        return isOwner||isAdmin;
    }
    public boolean canCurrentDeleteUser(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        String roleUser=userRepository.findByEmail(email).getRoles().getRole_name();
        return roleUser.equals("ADMIN");
    }

}
