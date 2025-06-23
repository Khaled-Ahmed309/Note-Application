package com.example.notes.repository;

import com.example.notes.model.NoteEntity;
import com.example.notes.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Integer> {

    NoteEntity findByNoteId(int noteId);
    List<NoteEntity> findByUserEntity(UserEntity user);
    List<NoteEntity> findByUserEntity_UserId(int userId);

}
