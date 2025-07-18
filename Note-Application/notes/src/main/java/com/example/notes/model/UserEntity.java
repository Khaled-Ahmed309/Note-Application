package com.example.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@DynamicInsert
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;


    @NotBlank(message = "Name must not be blank")
    @Size(min = 3,message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a correct email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5,message = "Name must be at least 5 characters long")
    private String password;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = NoteEntity.class)
    @JsonIgnore
    private Set<NoteEntity> notes;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Roles.class)
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    private Roles roles;


}
