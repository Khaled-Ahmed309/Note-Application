package com.example.notesapp.user.repository;

import com.example.notesapp.user.models.entities.PasswordResetToken;
import com.example.notesapp.user.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository  extends JpaRepository<PasswordResetToken,Integer> {


    PasswordResetToken findByToken(String token);
    Optional<PasswordResetToken> findByUser(UserEntity user);

}
