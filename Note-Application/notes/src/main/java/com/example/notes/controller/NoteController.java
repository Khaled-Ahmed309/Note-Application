package com.example.notes.controller;

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
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

public class NoteController {


    @Autowired
    NoteService noteService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;


    // Retrieve the notes
    @GetMapping("/getNote")
    public List<NoteEntity> getNotes( Authentication authentication) {
        UserEntity logged_user= (UserEntity) authentication.getPrincipal();
        return noteRepository.findByUserEntity_UserId(logged_user.getUserId());
    }


    //Create the note

    @PostMapping("/saveNote")
    public ResponseEntity<Response> saveNote(@Valid @RequestBody NoteEntity note, Authentication authentication) {
        Response response=new Response();
        UserEntity logged_user= (UserEntity) authentication.getPrincipal();
        UserEntity user=userRepository.findByEmail(logged_user.getEmail());
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
    public ResponseEntity<Response> deleteNote(@RequestParam("noteId") int noteId) {
        Response response=new Response();
        NoteEntity note =noteRepository.findByNoteId(noteId);
        if (note!=null&&note.getNoteId()>0) {
            return noteService.deleteNote(note);
        }
        response.setStatusCode("405");
        response.setStatusCode("Invalid noteId, insert valid noteId");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Invocation from ","delete note")
                .body(response);

    }

    //Updated the note
    @PostMapping("/updateNote")
    public ResponseEntity<Response> updateNote(@RequestParam("noteId") int noteId,
                              @RequestBody NoteEntity noteEntity) {
        Response response=new Response();
        NoteEntity note=noteRepository.findByNoteId(noteId);
        if (note!=null) {
            return noteService.updateNote(noteEntity,note);
        }
        response.setStatusCode("400");
        response.setStatusMsg("Invalid note Id");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Invocation from ","Updated note")
                .body(response);
    }

}





