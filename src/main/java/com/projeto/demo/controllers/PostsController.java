package com.projeto.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.demo.dtos.PostsDto;
import com.projeto.demo.models.PostsModel;
import com.projeto.demo.repositories.PostsRepository;

import jakarta.validation.Valid;

@RestController
public class PostsController {
    @Autowired
    private PostsRepository postsRepository;

    @PostMapping("/post")
    public ResponseEntity<PostsModel> createPost(@RequestBody @Valid PostsDto data) {
        var newPost = new PostsModel();
        BeanUtils.copyProperties(data, newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(postsRepository.save(newPost));
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostsModel>> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK).body(postsRepository.findAll());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Object> getPost(@PathVariable(value = "id") UUID id) {
        Optional<PostsModel> existedPost = postsRepository.findById(id);

        if (existedPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(existedPost.get());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "id") UUID id) {
        Optional<PostsModel> existedPost = postsRepository.findById(id);

        if (existedPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post not found");
        }

        postsRepository.delete(existedPost.get());
        return ResponseEntity.status(HttpStatus.OK).body("post deleted");
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid PostsDto data) {
        Optional<PostsModel> existPost = postsRepository.findById(id);

        if (existPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found post");
        }
        existPost.get().setTitle(data.title());
        existPost.get().setContent(data.content());
        return ResponseEntity.status(HttpStatus.OK).body(existPost.get());
    }
}
