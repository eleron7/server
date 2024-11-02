package com.example.server.Object.User.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name="USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_CODE", nullable = false, length = 10, unique = true)
    private String userCode;

    @Column(name = "USER_Email", nullable = false, length = 100, unique = true)
    private String userEmail;

    @Column(name = "USER_ID", nullable = false, length = 50, unique = true)
    private String userId;

    @Column(name = "USER_PWD", nullable = false, length = 100)
    private String userPwd;

    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String userName;

    @Column(name = "USER_BIRTH", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirth;

    @Column(name = "USER_PHONE_NUMBER", nullable = false, length = 50)
    private String userPhoneNumber;
}
