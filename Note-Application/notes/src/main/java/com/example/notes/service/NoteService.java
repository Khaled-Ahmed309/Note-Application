package com.example.notes.service;

import com.example.notes.model.NoteEntity;
import com.example.notes.model.NoteUser;
import com.example.notes.repository.NoteRepository;
import com.example.notes.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public boolean saveNote(NoteEntity noteEntity){
        if (noteEntity!=null){
            noteRepository.save(noteEntity);
            return true;
        }
        return false;
    }

    public ResponseEntity<Response> deleteNote(int noteId){
        Response response=new Response();
        Optional<NoteEntity> notes = noteRepository.findById(noteId);

        if (notes.isPresent()) {

            noteRepository.delete(notes.get());
            response.setStatusCode("204");
            response.setStatusMsg("successfully deleted.");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Invocation from: ","delete note")
                    .body(response);
        }
        response.setStatusCode("404");
        response.setStatusMsg("Invalid deletion request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Invocation from: ","delete note")
                .body(response);

    }

    public List<NoteEntity> getNote(int noteId){
        List<NoteEntity> note=noteRepository.findByNoteId(noteId);
        if (!(note.isEmpty())){
            return noteRepository.findByNoteId(noteId);
        }
        return List.of();
    }

    public ResponseEntity<Response> updateNote(NoteEntity noteEntity,int noteId){

        Response response =new Response();
        Optional<NoteEntity> notes = noteRepository.findById(noteId);
        if (notes.isPresent()) {
            if (noteEntity.getTitle() != null) {
                notes.get().setTitle(noteEntity.getTitle());
            }
            if (noteEntity.getContent() != null) {
                notes.get().setContent(noteEntity.getContent());
            }
            noteRepository.save(notes.get());
            response.setStatusCode("205");
            response.setStatusMsg("Updated successfully");
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .header("Invocation from ","Update note")
                    .body(response);

        }
        response.setStatusCode("400");
        response.setStatusMsg("Invalid note Id");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Invocation from ","Updated note")
                .body(response);
    }
}
