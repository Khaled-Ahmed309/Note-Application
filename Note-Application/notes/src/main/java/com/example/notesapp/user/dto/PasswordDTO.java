package com.example.notesapp.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {
    @NotBlank(message = "Password must not be blank")
    @Size(min = 5,message = "Name must be at least 5 characters long")
    private String password;
}
