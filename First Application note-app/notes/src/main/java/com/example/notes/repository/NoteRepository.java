package com.example.notes.repository;

import com.example.notes.model.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Integer> {

    List<NoteEntity> findByNoteId(int noteId);
    List<NoteEntity> findByUserEntity_UserId(int userId);

}
