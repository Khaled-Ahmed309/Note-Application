package com.example.notes.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3,message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a correct email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5,message = "Name must be at least 5 characters long")
    private String password;
}
