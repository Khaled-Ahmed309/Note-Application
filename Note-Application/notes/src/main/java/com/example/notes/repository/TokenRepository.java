package com.example.notes.repository;

import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository  extends JpaRepository<PasswordResetToken,Integer> {


    PasswordResetToken findByToken(String token);
    List<PasswordResetToken> findByUser(UserEntity user);

}
