package com.example.notes.repository;

import com.example.notes.model.PasswordResetToken;
import com.example.notes.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository  extends JpaRepository<PasswordResetToken,Integer> {


    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(UserEntity user);

}
