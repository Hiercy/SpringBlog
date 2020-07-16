package com.mike.blog.service;

import com.mike.blog.dto.PostDto;
import com.mike.blog.exception.PostNotFoundException;
import com.mike.blog.exception.UserNotFoundException;
import com.mike.blog.model.Post;
import com.mike.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final AuthService authService;
    private final PostRepository postRepository;

    @Autowired
    public PostService(AuthService authService, PostRepository postRepository) {
        this.authService = authService;
        this.postRepository = postRepository;
    }

    @Transactional
    public void create(PostDto postDto) {
        postRepository.save(dtoToPost(postDto));
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(this::mapPostToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto getById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Cannot find post with id = " + id));
        return mapPostToDto(post);
    }

    @Transactional(readOnly = true)
    public PostDto getByTitle(String title) {
        Post post = postRepository.findByTitle(title)
                .orElseThrow(() -> new PostNotFoundException("Cannot find post with title = " + title));
        return mapPostToDto(post);
    }

    private PostDto mapPostToDto(Post post) {
        return new PostDto().builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUsername())
                .build();
    }

    private Post dtoToPost(PostDto postDto) {
        User currentUser = authService.getCurrentUser().orElseThrow(() ->
                new UserNotFoundException("Cannot get user from context"));
        return new Post().builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createdAt(Instant.now())
                .updateAt(Instant.now())
                .username(currentUser.getUsername())
                .build();
    }
}
