package com.example.notes.controller;


import com.example.notes.model.NoteEntity;
import com.example.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/UserNotes")
    public List<NoteEntity> userNotes(@RequestParam("user_id") int user_id){
        List<NoteEntity> user_notes=userService.userNote(user_id);
        if (!(user_notes.isEmpty())) {
            return user_notes;
        }
        return List.of();
    }
}
