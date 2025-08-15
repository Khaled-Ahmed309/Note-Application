package com.example.notesapp.notes.repository;

import com.example.notesapp.notes.models.entities.NoteEntity;
import com.example.notesapp.user.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Integer> {

    List<NoteEntity> findByUserEntity(UserEntity user);
    List<NoteEntity> findByUserEntity_Userid(int userId);
    void deleteByUserEntity(UserEntity user);

}
