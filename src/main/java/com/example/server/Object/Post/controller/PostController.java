package com.example.server.Object.Post.controller;

import com.example.server.Object.Post.service.PostService;
import com.example.server.Object.Post.trasfer.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/post/")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping(value = "/createPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestPart("post") PostDto postDto, @RequestPart("file") List<MultipartFile> multipartFiles) throws IOException {
        return ResponseEntity.ok().body(postService.createPost(multipartFiles, postDto));
    }

    @DeleteMapping(value = "/deletePost")
    public ResponseEntity<Boolean> deletePost(@RequestBody PostDto postDto) throws IOException {
        return ResponseEntity.ok().body(postService.deletePost(postDto));
    }
}
