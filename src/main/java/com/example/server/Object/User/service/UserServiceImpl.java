package com.example.server.Object.User.service;

import com.example.server.Object.User.entity.User;
import com.example.server.Object.User.mapper.UserMapper;
import com.example.server.Object.User.tranfer.UserDto;
import com.example.server.Object.User.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            User user = userMapper.dtoToEntity(userDto);
            //유저 코드 생성
            user.createUserCode(userRepository.createUserCode()
                    .orElseThrow(() -> new IllegalArgumentException("Failed to user code creation.")));
            //패스워드 암호화
            user.passwordEncoding(this.passwordEncoder);
            //유저 생성 및 반환
            return userMapper.entityToDto(userRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Boolean deleteUser(UserDto userDto) {
        try {
            //삭제 타겟 조회
            User targetUser = userRepository.findByUserId(userDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with : " + userDto.getUserId()));
            //삭제 타겟 패스워드 검증 후 삭제
            if (passwordEncoder.matches(userDto.getUserPwd(), targetUser.getUserPwd())) {
                userRepository.deleteByUserId(targetUser.getUserId());
            }
            //정상 삭제 여부 확인 후 반환
            return !userRepository.existsByUserId(targetUser.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        try {
            //업데이트 타겟 조회
            User targetUser = userRepository.findByUserId(userDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with : " + userDto.getUserId()));
            //dto -> entity 업데이트
            BeanUtils.copyProperties(userDto, targetUser, getNullPropertyNames(userDto));
            //패스워드 암호화
            targetUser.passwordEncoding(this.passwordEncoder);
            //타겟 업데이트
            userRepository.save(targetUser);
            //업데이트 유저 반환
            return userMapper.entityToDto(userRepository.findByUserId(targetUser.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Failed to user info update with : " + userDto.getUserId())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto findByUserId(String userId) {
        return userMapper.entityToDto(userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Failed to user info update with : " + userId)));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
