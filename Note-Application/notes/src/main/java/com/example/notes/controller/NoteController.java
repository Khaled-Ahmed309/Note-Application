package com.example.notes.controller;

import com.example.notes.model.NoteEntity;
import com.example.notes.model.NoteUser;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.response.Response;
import com.example.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

public class NoteController {


    @Autowired
    NoteService noteService;


    // Retrieve the notes
    @GetMapping("/getNotesById")
    public List<NoteEntity> getNotes(@RequestParam(name = "noteId") int noteId) {
       return noteService.getNote(noteId);
    }


    //Create the note

    @PostMapping("/saveNote")
    public ResponseEntity<Response> saveNote(@Valid @RequestBody NoteUser noteUser ) {
        Response response=new Response();
        NoteEntity note=noteUser.getNoteEntity();
        UserEntity user=noteUser.getUserEntity();
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
        return noteService.deleteNote(noteId);

    }


    @PostMapping("/updateNote")
    public ResponseEntity<Response> updateNote(@RequestParam("noteId") int noteId,
                             @Valid @RequestBody NoteEntity noteEntity) {
        return noteService.updateNote(noteEntity,noteId);

    }

}





