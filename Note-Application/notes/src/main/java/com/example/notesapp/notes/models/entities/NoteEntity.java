package com.example.notesapp.notes.models.entities;

import com.example.notesapp.user.models.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
public class NoteEntity extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = true)
    private UserEntity userEntity;

}
