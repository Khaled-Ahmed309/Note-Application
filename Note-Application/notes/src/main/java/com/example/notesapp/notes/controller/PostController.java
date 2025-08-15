package com.example.notesapp.notes.controller;


import com.example.notesapp.notes.dto.SimplePostDTO;
import com.example.notesapp.notes.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")

public class PostController {

    private final PostService postService;
    public PostController(PostService postService){
        this.postService=postService;
    }

    @GetMapping("/post")
    public List<SimplePostDTO> getPosts(){
        return postService.fetchPosts();
    }

}
