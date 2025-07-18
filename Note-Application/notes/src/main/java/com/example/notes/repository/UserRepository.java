package com.example.notes.repository;

import com.example.notes.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByEmail(String email);
    UserEntity findByUserId(int userId);

}
