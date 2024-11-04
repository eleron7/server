package com.example.server.Object.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;

@Data
@Entity
@Table(name="USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 빌더를 사용할 수 있도록 모든 필드 포함 생성자 추가
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

    public void passwordEncoding(PasswordEncoder passwordEncoder){
        if (this.userPwd == null) {
            throw new IllegalArgumentException("password cannot be null");
        }
        this.userPwd = passwordEncoder.encode(this.userPwd);
    }

    public void createUserCode(String userCode){
        this.userCode = userCode;
    }
}
