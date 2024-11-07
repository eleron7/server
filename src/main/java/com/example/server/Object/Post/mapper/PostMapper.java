package com.example.server.Object.Post.mapper;

import com.example.server.Object.Post.entity.Post;
import com.example.server.Object.Post.trasfer.PostDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface PostMapper {
    @Mapping(target = "postFiles", ignore = true)
    @Mapping(source = "createUserDto", target = "createUser")
    Post dtoToEntity (PostDto postDto);

    @Mapping(source = "createUser", target = "createUserDto")
    @Mapping(source = "postFiles", target = "postFilesDto")
    PostDto entityToDto (Post post);
}
