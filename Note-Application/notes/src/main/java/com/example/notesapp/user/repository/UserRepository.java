package com.example.notesapp.user.repository;

import com.example.notesapp.user.models.entities.UserEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<UserEntity,Integer> {


    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserid(int userId);

}
