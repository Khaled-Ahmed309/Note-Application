package com.example.notes.service;

import com.example.notes.config.AuthorizationUtils;
import com.example.notes.dto.NoteDTO;
import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

     private final NoteRepository noteRepository;
     private final UserRepository userRepository;

     public NoteService(NoteRepository noteRepository,UserRepository userRepository){
         this.noteRepository=noteRepository;
         this.userRepository=userRepository;
     }

    public void createNote(NoteDTO noteDTO) {

        try {
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            String userEmail=auth.getName();
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


   public void deleteNote(int  noteId){
         NoteEntity note = noteRepository.findByNoteId(noteId);
        try {
            note.setUserEntity(null);
            noteRepository.delete(note);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting your note ");
        }
    }

    public void editeNote(int notId, NoteDTO noteDTO) {
        NoteEntity note = noteRepository.findByNoteId(notId);
            if (noteDTO.getTitle() != null) {
                note.setTitle(noteDTO.getTitle());
            }
            if (noteDTO.getContent() != null) {
                note.setContent(noteDTO.getContent());
            }
            noteRepository.save(note);
    }
}
