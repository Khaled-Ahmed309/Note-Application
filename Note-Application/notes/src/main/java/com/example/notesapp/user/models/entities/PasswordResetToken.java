package com.example.notesapp.user.models.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PasswordResetToken extends SubBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private int id;
    private String token;
    @Column(name = "expirydatetime")
    private LocalDateTime expiryDateTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false)
    private UserEntity user;
    private boolean used = false;
}
