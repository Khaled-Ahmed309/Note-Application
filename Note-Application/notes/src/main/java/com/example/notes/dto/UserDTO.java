package com.example.notes.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @Size(min = 3,message="Name must be at least 3 characters long")
    private String name;

    @Email(message = "Please provide a correct email")
    private String email;

    @Size(min = 5,message = "Name must be at least 5 characters long")
    private String password;
}
