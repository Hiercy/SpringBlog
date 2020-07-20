package com.mike.blog.controller;

import com.mike.blog.dto.PostDto;
import com.mike.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostDto postDto) {
        postService.create(postDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAll() {
        return new ResponseEntity(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getById(id));
    }

    @GetMapping("/bytitle/{title}")
    public ResponseEntity<PostDto> getByTitle(@PathVariable("title") String title) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getByTitle(title));
    }
}
