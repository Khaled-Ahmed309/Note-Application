package com.example.notes.service;

import com.example.notes.dto.NoteDTO;
import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import com.example.notes.response.Response;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;



    public void saveNote(NoteDTO noteDTO,String userEmail) {

        try {

            UserEntity user = userRepository.findByEmail(userEmail);
            NoteEntity note = new NoteEntity();
            note.setTitle(noteDTO.getTitle());
            note.setContent(noteDTO.getContent());
            note.setUserEntity(user);
            noteRepository.save(note);
        } catch (Exception e) {
            throw new RuntimeException("Error while save your note");
        }

    }
   public void deleteNote(NoteEntity  note){
        try {

            note.setUserEntity(null);
            noteRepository.delete(note);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting your note ");
        }
    }

    public void updateNote(NoteDTO updateNote, HttpSession httpSession){
        NoteEntity oldNote=(NoteEntity) httpSession.getAttribute("noteInformation");
            if (updateNote.getTitle() != null) {
                oldNote.setTitle(updateNote.getTitle());
            }
            if (updateNote.getContent() != null) {
                oldNote.setContent(updateNote.getContent());
            }
            httpSession.setAttribute("noteInformation",oldNote);
            noteRepository.save(oldNote);

    }
}
