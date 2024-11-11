package com.example.server.Object.Post.mapper;

import com.example.server.Object.Post.entity.PostFile;
import com.example.server.Object.Post.trasfer.FileDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface FileMapper {
    FileDto entityToDto (PostFile postFile);
}
