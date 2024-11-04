package com.example.server.Object.User.service;


import com.example.server.Object.User.tranfer.UserDto;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto userDto);

    Boolean deleteUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto findByUserId(String userId);
    List<UserDto> findAll();
}
