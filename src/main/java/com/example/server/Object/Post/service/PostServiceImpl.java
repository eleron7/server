package com.example.server.Object.Post.service;

import com.example.server.Object.Post.entity.Post;
import com.example.server.Object.Post.entity.PostFile;
import com.example.server.Object.Post.mapper.PostMapper;
import com.example.server.Object.Post.repository.PostRepository;
import com.example.server.Object.Post.trasfer.PostDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Value("${file.img-path}")
    private String filePath;

    @Autowired
    public PostServiceImpl (PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostDto createPost(List<MultipartFile> multipartFiles, PostDto postDto) {
        try {
            Post post = postMapper.dtoToEntity(postDto);
            post.setCreateDateTime(LocalDateTime.now());

            List<PostFile> postFiles = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                PostFile postFile = PostFile.builder().build();
                postFile.saveFile(multipartFile, filePath);
                postFiles.add(postFile);
            }

            post.setPostFiles(postFiles);
            Post returnPost = postRepository.save(post);

            for (PostFile postFile : returnPost.getPostFiles()) {
                postFile.readBytes();
            }

            return postMapper.entityToDto(returnPost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Boolean deletePost(PostDto postDto) {
        try {
            Post post = postRepository.findById(postDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Post not found with : " + postDto.getId()));

            if (post.getCreateUser().getUserId().equals(postDto.getCreateUserDto().getUserId())) {
                postRepository.deleteById(post.getId());
            }

            return !postRepository.existsById(post.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto findById(Long Id) {
        return null;
    }

    @Override
    public List<PostDto> findAll() {
        return null;
    }

    @Override
    public List<PostDto> findByContent(String keyword) {
        return null;
    }

    @Override
    public List<PostDto> findByCreateUser(String userId) {
        return null;
    }
}
