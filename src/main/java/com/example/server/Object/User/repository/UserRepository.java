package com.example.server.Object.User.repository;

import com.example.server.Object.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    List<User> findAll();

    Boolean existsByUserId(String userId);

    void deleteByUserId(String userId);

    //yyMMdd + 금일 계정 생성한 인원 수 + 1로(4자리) 10자리 코드 생성
    @Query(value = "SELECT CONCAT(DATE_FORMAT(NOW(), '%y%m%d'), LPAD(CAST(COUNT(U.user_id) + 1 AS CHAR), 4, '0')) AS USER_CODE " +
            "FROM USER U " +
            "WHERE SUBSTRING(U.user_code, 1, 6) = DATE_FORMAT(NOW(), '%y%m%d')",
            nativeQuery = true)
    Optional<String> createUserCode();

}
