package com.example.server.Object.Post.entity;

import com.example.server.Object.User.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "CONTENT", nullable = false, length = 250)
    private String content;

    @ManyToOne
    @JoinColumn(name = "CREATE_USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private User createUser;

    @Column(name = "CREATE_DATE_TIME", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createDateTime;

    @ManyToOne
    @JoinColumn(name = "UPDATE_USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private User updateUser;

    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updateDateTime;
}