package com.example.server.Object.Post.trasfer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {
    private String fileName;
    private byte [] bytes;
}
