package com.apinizer.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileRestResource {

    private static final String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String RESPONSE_FAIL = "{\"result\":\"FAIL\" }";


    // ---------------- FILE OPERATION ----------------
    @PostMapping(value = "/test/fileUpload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RESPONSE_FAIL);
        }
        String filePath = UPLOADED_FOLDER + "uploaded_" + file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RESPONSE_FAIL);
        }

        return ResponseEntity.status(HttpStatus.OK).body("{\"result\":\"SUCCESS\", \"filePath\":\"" + filePath + "\" }");
    }


    @PostMapping(value = "/test/multipart", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> multipart(@RequestPart(value = "id") String id,
                                            @RequestPart(value = "token") String token,
                                            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RESPONSE_FAIL);
        }
        String filePath = UPLOADED_FOLDER + "uploaded_" + file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RESPONSE_FAIL);
        }


        return ResponseEntity.status(HttpStatus.OK).body("{\"id\":\"" + id + "\", \"token\":\"" + token + "\", \"filePath\":\"" + filePath + "\" }");
    }
}
