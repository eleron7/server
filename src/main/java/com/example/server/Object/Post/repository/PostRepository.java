package com.example.server.Object.Post.repository;

import com.example.server.Object.Post.entity.Post;
import com.example.server.Object.User.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = "postFiles")
    List<Post> findAll();
    @EntityGraph(attributePaths = "postFiles")
    Optional<Post> findById(Long id);
    List<Post> findByTitle(String title);
    List<Post> findByContent(String content);
    List<Post> findByCreateUser(User createUser);
}
