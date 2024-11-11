package com.example.server.Object.Post.service;

import com.example.server.Object.Post.entity.Post;
import com.example.server.Object.Post.entity.PostFile;
import com.example.server.Object.Post.mapper.PostMapper;
import com.example.server.Object.Post.repository.PostRepository;
import com.example.server.Object.Post.trasfer.PostDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
                for (PostFile postfile : post.getPostFiles()) {
                    postfile.deleteFile();
                }
                postRepository.deleteById(post.getId());
            }

            return !postRepository.existsById(post.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public PostDto updatePost(List<MultipartFile> multipartFiles, PostDto postDto) {
        try {
            Post post = postRepository.findById(postDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Post not found with : " + postDto.getId()));
            Post newPost = postMapper.dtoToEntity(postDto);

            for (PostFile postfile : post.getPostFiles()) {
                postfile.deleteFile();
            }
            post.getPostFiles().clear();

            for (MultipartFile multipartFile : multipartFiles) {
                PostFile postFile = PostFile.builder().build();
                postFile.saveFile(multipartFile, filePath);
                post.getPostFiles().add(postFile);
            }

            BeanUtils.copyProperties(newPost, post, getNullPropertyNames(newPost));
            postRepository.save(post);

            return postMapper.entityToDto(postRepository.findById(post.getId())
                    .orElseThrow(()-> new EntityNotFoundException("Failed to post update with : " + postDto.getId())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostDto findById(Long id) {
        return postMapper.entityToDto(postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Failed to post with : " + id)));
    }

    @Override
    public List<PostDto> findAll() {
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : postRepository.findAll()) {
            postDtoList.add(postMapper.entityToDto(post));
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> findByContent(String keyword) {
        return null;
    }

    @Override
    public List<PostDto> findByCreateUser(String userId) {
        return null;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
