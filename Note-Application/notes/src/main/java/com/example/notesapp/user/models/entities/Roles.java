package com.example.notesapp.user.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer role_id;
    private  String role_name;
}
