package com.example.notesapp.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(int id) {
        super("Note with id:"+id+" not found");
    }
}
