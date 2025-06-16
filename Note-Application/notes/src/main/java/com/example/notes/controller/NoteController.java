package com.example.notes.controller;

import com.example.notes.config.AuthorizationUtils;
import com.example.notes.dto.NoteDTO;
import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.MyUserDetailsService;
import com.example.notes.service.NoteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/api/notes",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

public class NoteController {


    @Autowired
    NoteService noteService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    AuthorizationUtils authorizationUtils;

    // Retrieve the notes
    @GetMapping("/getNote")
    public List<NoteEntity> getNotes(Authentication authentication) {

        String user_email=authentication.getName();
        UserEntity user=userRepository.findByEmail(user_email);
        log.info("The user name and email: {}",userDetailsService.loadUserByUsername(user_email));
        return noteRepository.findByUserEntity(user);
    }


    //Create the note

    @PostMapping("/saveNote")
    public ResponseEntity<?> saveNote(@Valid @RequestBody NoteDTO noteDTO, Authentication authentication) {
        String user_email=authentication.getName();
        noteService.saveNote(noteDTO,user_email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Invocation from: ","saveNote")
                .body("Successfully save your note");

    }

    //Delete the note
    @DeleteMapping("/deleteNote")
    public ResponseEntity<?> deleteNote(@RequestParam("noteId") int noteId,Authentication authentication) {
        NoteEntity note = noteRepository.findByNoteId(noteId);
        boolean isNoteOwner = authorizationUtils.canCurrentUserAccessNote(note, authentication);
        if (isNoteOwner) {
            noteService.deleteNote(note);
            return ResponseEntity.status(HttpStatus.OK).body("Deleting note successfully");
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("You are not Allowed to delete the note");

    }

    //Updated the note
    @PostMapping("/updateNote/{id}")
    public ResponseEntity<?> updateNote(@PathVariable("id") int noteId,
                                        @RequestBody NoteDTO noteDTO, Authentication authentication, HttpSession httpSession) {
        NoteEntity note = noteRepository.findByNoteId(noteId);
        httpSession.setAttribute("noteInformation", note);
        if (noteDTO != null && note != null) {
            boolean isNoteOwner = authorizationUtils.canCurrentUserAccessNote(note, authentication);
            if (isNoteOwner) {
                noteService.updateNote(noteDTO, httpSession);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated note successfully");
            } else {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Error you are not allow to updated this note");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error happening");
    }

}





