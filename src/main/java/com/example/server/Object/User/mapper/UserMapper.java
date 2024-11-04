package com.example.server.Object.User.mapper;

import com.example.server.Object.User.tranfer.UserDto;
import com.example.server.Object.User.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UserMapper {
    @Mapping(target = "id", ignore = true) // id 필드 매핑 무시
    User dtoToEntity (UserDto userDto);

    @Mapping(target = "userPwd", ignore = true) // Pwd 필드 매핑 무시
    UserDto entityToDto (User user);
}
