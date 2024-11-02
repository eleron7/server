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

    @Query(value = "SELECT LPAD(CAST(COUNT(U.user_id) + 1 AS CHAR), 4, '0') " +
            "FROM USER U " +
            "WHERE SUBSTRING(U.user_code, 1, 6) = :yyMMdd",
            nativeQuery = true)
    Optional<String> findUserNextCountByyMMdd(@Param("yyMMdd") String yyMMdd);
}
