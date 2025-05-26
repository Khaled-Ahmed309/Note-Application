package com.example.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
public class NoteEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    @Size(min = 5,message = "The tile must be more than 5 letter")
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id",referencedColumnName = "userId",nullable = true)
    @JsonIgnore
    private UserEntity userEntity;

}
