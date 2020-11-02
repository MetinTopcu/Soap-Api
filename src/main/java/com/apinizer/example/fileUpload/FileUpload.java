package com.apinizer.example.fileUpload;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileUpload {

    private static final String UPLOADED_FOLDER = "/uploadedFiles";
    private static final String RESPONSE_FAIL = "{\"result\":\"FAIL\" }";

    // ---------------- HELLO APIs ----------------
    private static int i = 0;

    @GetMapping("/test/hello")
    public ResponseEntity<String> hello() {

        return ResponseEntity.ok("OK " + (i++));
    }

    @GetMapping("/test/helloWait")
    public ResponseEntity<String> helloWait(@RequestParam("waitTimeInMillis") long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("OK");
    }


    // ---------------- FILE OPERATION ----------------
    @PostMapping(value = "/test/fileUpload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

}
