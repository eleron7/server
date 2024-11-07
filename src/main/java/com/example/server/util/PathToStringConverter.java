package com.example.server.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Converter(autoApply = true)
public class PathToStringConverter implements AttributeConverter<Path, String> {
    // Path -> String 변환 (저장 시)
    @Override
    public String convertToDatabaseColumn(Path path) {
        return (path != null) ? path.toString() : null;
    }

    // String -> Path 변환 (조회 시)
    @Override
    public Path convertToEntityAttribute(String dbData) {
        return (dbData != null) ? Paths.get(dbData) : null;
    }
}
