package com.example.notesapp.notes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplePostDTO {
    private String title;
    private String body;

    public SimplePostDTO(String body, String title) {
        this.body = body;
        this.title = title;
    }
}
