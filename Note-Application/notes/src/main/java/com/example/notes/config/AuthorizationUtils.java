package com.example.notes.config;

import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    @Autowired
    UserRepository userRepo;

    public boolean canCurrentUserAccessNote(NoteEntity  note,Authentication authentication){

        String loggedUserEmail=authentication.getName();
        UserEntity currentUser=userRepo.findByEmail(loggedUserEmail);

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

}
