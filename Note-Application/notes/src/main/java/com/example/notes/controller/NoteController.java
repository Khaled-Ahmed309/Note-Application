package com.example.notes.controller;

import com.example.notes.config.AuthorizationUtils;
import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.response.Response;
import com.example.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    AuthorizationUtils authorizationUtils;

    // Retrieve the notes
    @GetMapping("/getNote")
    public List<NoteEntity> getNotes(Authentication authentication) {

        String user_email=authentication.getName();
        UserEntity user=userRepository.findByEmail(user_email);
        return noteRepository.findByUserEntity(user);
    }


    //Create the note

    @PostMapping("/saveNote")
    public ResponseEntity<Response> saveNote(@Valid @RequestBody NoteEntity note, Authentication authentication) {
        Response response=new Response();
        String user_email=authentication.getName();
        UserEntity user=userRepository.findByEmail(user_email);

        note.setUserEntity(user);
        noteService.saveNote(note);
        response.setStatusMsg("Saved successfully");
        response.setStatusCode("200");
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Invocation from: ","saveNote")
                .body(response);

    }

    //Delete the note
    @DeleteMapping("/deleteNote")
    public ResponseEntity<Response> deleteNote(@RequestParam("noteId") int noteId,Authentication authentication) {
        Response response = new Response();
        NoteEntity note = noteRepository.findByNoteId(noteId);

        boolean isNoteOwner = authorizationUtils.canCurrentUserAccessNote(note, authentication);
        if (isNoteOwner) {
            return noteService.deleteNote(note);
        } else {
            response.setStatusCode("405");
            response.setStatusMsg("You are not Allowed to delete this note");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .header("Invocation from ", "delete note")
                    .body(response);

        }
    }

    //Updated the note
    @PostMapping("/updateNote")
    public ResponseEntity<Response> updateNote(@RequestParam("noteId") int noteId,
                              @RequestBody NoteEntity noteEntity,Authentication authentication) {
        Response response = new Response();
        NoteEntity note = noteRepository.findByNoteId(noteId);
        boolean isNoteOwner = authorizationUtils.canCurrentUserAccessNote(note, authentication);

        if (isNoteOwner) {
            return noteService.updateNote(noteEntity, note);
        } else {
            response.setStatusCode("400");
            response.setStatusMsg("You are not allow to update this note");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .header("Invocation from ", "Updated note")
                    .body(response);
        }
    }

}





