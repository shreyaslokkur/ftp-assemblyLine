package com.lks.test;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by lokkur on 6/20/2015.
 */

public class UploadTest extends AbstractTest {

    @Test
    public void testUpload(){
        Path path = Paths.get("test.txt");
        String name = "test.txt";
        String originalFileName = "test.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        controller.fileUpload(result,"test.txt","abc","abc",123,12,12);
    }
}
