package com.example.notes.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer role_id;
    private  String role_name;
}
