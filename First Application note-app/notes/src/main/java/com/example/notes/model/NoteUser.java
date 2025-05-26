package com.example.notes.model;



import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteUser {

   @Valid
   private NoteEntity noteEntity;

   @Valid
   private UserEntity userEntity;
}
