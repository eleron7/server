package com.example.server.Object.Post.trasfer;
import com.example.server.Object.User.tranfer.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto createUserDto;
    private List<FileDto> postFilesDto;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
}
