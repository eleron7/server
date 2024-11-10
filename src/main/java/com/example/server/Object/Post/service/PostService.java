package com.example.server.Object.Post.service;
import com.example.server.Object.Post.trasfer.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDto createPost(List<MultipartFile> multipartFiles, PostDto postDto) throws IOException;
    Boolean deletePost(PostDto postDto);
    PostDto updatePost(List<MultipartFile> multipartFiles, PostDto postDto);
    PostDto findById(Long id);
    List<PostDto> findAll();
    List<PostDto> findByContent(String keyword);
    List<PostDto> findByCreateUser(String userId);
}
