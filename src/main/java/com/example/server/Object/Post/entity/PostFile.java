package com.example.server.Object.Post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Data
@Entity
@Table(name="FILE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ORG_FILE_NAME", length = 250)
    private String orgFileName;
    @Column(name = "SAVED_FILE_NAME", length = 250)
    private String savedFileName;
    @Column(name = "SAVED_FILE_PATH", length = 250)
    private Path savedFilePath;
    @Transient // DB에 bytes 타입으로 저장하지는 않을 겻이나 byte 변환 용이하기 위해 객체의 프로퍼티로만 가짐
    private byte [] bytes;

    public void saveFile(MultipartFile multipartFile, String filePath) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            this.orgFileName = multipartFile.getOriginalFilename();
            this.savedFilePath = Path.of(filePath);
            this.savedFileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            this.bytes = multipartFile.getBytes();

            //경로가 없으면 생성
            if (Files.notExists(this.savedFilePath)) {
                Files.createDirectories(this.savedFilePath);
            }

            // 파일을 경로에 저장
            Files.write(this.savedFilePath.resolve(this.savedFileName), this.bytes, StandardOpenOption.CREATE_NEW);
        }
    }

    public void readBytes() {
        try {
            this.bytes = FileCopyUtils.copyToByteArray(new File(this.savedFilePath.toString()+'/'+this.savedFileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
