package ru.gb.mark.webstore.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

@Component
@Log4j2
public class FileService {

    @Value("${classpath:build/resources/main/static/images/}")
    private String imgPath;

    public void setAndWriteImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            file.transferTo(Path.of(imgPath.concat(Objects.requireNonNull(file.getOriginalFilename()))).toAbsolutePath());
        }
    }

}
