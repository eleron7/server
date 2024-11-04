package com.example.server.Object.User.controller;

import com.example.server.Object.User.tranfer.UserDto;
import com.example.server.Object.User.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok().body(userService.createUser(userDto));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.deleteUser(userDto));
    }

    @PostMapping("/userCheck")
    public ResponseEntity<Boolean> userCheck(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.userCheck(userDto));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.updateUser(userDto));
    }

    @GetMapping("/searchUser")
    public ResponseEntity<List<UserDto>> searchUser() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/searchUser/{userId}")
    public ResponseEntity<UserDto> searchUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(userService.findByUserId(userId));
    }
}
