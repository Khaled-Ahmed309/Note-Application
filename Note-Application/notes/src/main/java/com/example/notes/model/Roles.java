package com.example.notes.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;
    private  String role_name;
}
