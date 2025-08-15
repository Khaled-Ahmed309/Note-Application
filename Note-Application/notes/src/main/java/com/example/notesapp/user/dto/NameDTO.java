package com.example.notesapp.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameDTO {

    @Size(min = 3,message="Name must be at least 3 characters long")
    private String name;
}
