package com.example.notes.service;

import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;



    public NoteEntity saveNote(NoteEntity note){

       return noteRepository.save(note);
    }

   public ResponseEntity<Response> deleteNote(NoteEntity  note){
        Response response=new Response();
        noteRepository.delete(note);
        response.setStatusMsg("Deleted note Successfully");
        response.setStatusCode("200");
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Invocation from","delete note")
                .body(response);

    }

    public ResponseEntity<Response> updateNote(NoteEntity newNote,NoteEntity oldNote){

        Response response =new Response();
            if (newNote.getTitle() != null) {
                oldNote.setTitle(newNote.getTitle());
            }
            if (newNote.getContent() != null) {
                oldNote.setContent(newNote.getContent());
            }
            noteRepository.save(oldNote);
            response.setStatusCode("205");
            response.setStatusMsg("Updated successfully");
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .header("Invocation from ","Update note")
                    .body(response);

    }
}
