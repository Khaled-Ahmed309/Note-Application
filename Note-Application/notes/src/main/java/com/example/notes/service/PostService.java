package com.example.notes.service;

import com.example.notes.dto.SimplePostDTO;
import com.example.notes.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final RestClient restClient;
    public PostService(RestClient.Builder builder){
        this.restClient=builder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }
    public List<SimplePostDTO> fetchPosts(){
        Post[] posts=restClient
                .get().uri("/posts")
                .retrieve()
                .body(Post[].class);

        return Arrays.stream(posts)
                .map(post -> new SimplePostDTO(post.getTitle(), post.getBody()))
                .collect(Collectors.toList());
    }
}
