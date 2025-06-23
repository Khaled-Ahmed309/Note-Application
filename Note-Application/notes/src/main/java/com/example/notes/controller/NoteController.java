package com.example.notes.controller;

import com.example.notes.config.AuthorizationUtils;
import com.example.notes.dto.NoteDTO;
import com.example.notes.service.NoteService;
import com.example.notes.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping(path = "/api/notes",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")

public class NoteController {


    private final NoteService noteService;
    private final UserService userService;
    private final AuthorizationUtils authorizationUtils;

    public NoteController(NoteService noteService,UserService userService,AuthorizationUtils authorizationUtils){
        this.noteService=noteService;
        this.userService=userService;
        this.authorizationUtils=authorizationUtils;
    }

    // Retrieve the notes
    @GetMapping("/getNote")
    public List<NoteDTO> getNote() {

        return userService.myNote();
    }


    //Create the note

    @PostMapping("/saveNote")
    public ResponseEntity<?> saveNote(@Valid @RequestBody NoteDTO noteDTO) {
        noteService.createNote(noteDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully save your note");
    }

    //Delete the note
    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(name = "id") int noteId) {
        if (noteId>0&&authorizationUtils.canCurrentUserAccessNote(noteId)) {
            noteService.deleteNote(noteId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleting note successfully");
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("You are not Allowed to delete the note");

    }

    //Updated the note
    @PostMapping("/updateNote/{id}")
    public ResponseEntity<?> updateNote(@PathVariable("id") int noteId, @RequestBody NoteDTO noteDTO) throws Exception {
        if (noteId>=0 &&authorizationUtils.canCurrentUserAccessNote(noteId)){
            noteService.editeNote(noteId,noteDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated note successfully");
        }
        else
            throw new Exception("Error you are not allow to updated this note");


    }

}





