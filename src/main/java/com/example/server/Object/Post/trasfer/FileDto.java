package com.example.server.Object.Post.trasfer;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@Builder
public class FileDto {
    private String orgFileName;
    private String savedFileName;
    private Path savedFilePath;
}
