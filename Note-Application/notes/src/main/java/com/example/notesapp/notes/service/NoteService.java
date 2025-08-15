package com.example.notesapp.notes.service;

import com.example.notesapp.exception.UserNotFoundException;
import com.example.notesapp.notes.dto.NoteDTO;
import com.example.notesapp.exception.NoteNotFoundException;
import com.example.notesapp.notes.models.entities.NoteEntity;
import com.example.notesapp.user.models.entities.UserEntity;
import com.example.notesapp.notes.repository.NoteRepository;
import com.example.notesapp.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteService {


     private final NoteRepository noteRepository;
     private final UserRepository userRepository;




     public NoteService(NoteRepository noteRepository,UserRepository userRepository){
         this.noteRepository=noteRepository;
         this.userRepository=userRepository;
     }

    public void createNote(NoteDTO noteDTO) {
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            String userEmail=auth.getName();
            Optional<UserEntity> user = userRepository.findByEmail(userEmail);
            if (user.isEmpty()){
                throw new UserNotFoundException(0);
            }

//            entityManager.refresh(user.get());
            NoteEntity note = new NoteEntity();
            note.setTitle(noteDTO.getTitle());
            note.setContent(noteDTO.getContent());
            note.setUserEntity(user.get());
        System.out.println("User ID about to save: " + note.getUserEntity().getUserid());

        noteRepository.save(note);


    }




   public void deleteNote(int  noteId) {
       Optional<NoteEntity> note = noteRepository.findById(noteId);
       if (note.isPresent()) {
           noteRepository.deleteById(noteId);
       } else {
           // it will throw message: note with the id not found
           throw new NoteNotFoundException(noteId);
       }
   }

    public void editeNote(int notId, NoteDTO noteDTO) {
        Optional<NoteEntity> note = noteRepository.findById(notId);
        if (note.isPresent()) {
            if (noteDTO.getTitle() != null) {
                note.get().setTitle(noteDTO.getTitle());
            }
            if (noteDTO.getContent() != null) {
                note.get().setContent(noteDTO.getContent());
            }
            noteRepository.save(note.get());
        }else {
            throw new NoteNotFoundException(notId);
        }
    }
}
