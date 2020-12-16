package com.apinizer.example.api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        UploadFileResponse fileResponse;
        try {
            fileResponse = uploadFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RESPONSE_FAIL);
        }


        return ResponseEntity.status(HttpStatus.OK).body("{\"id\":\"" + id + "\", \"token\":\"" + token + "\", \"downloadUri\":\"" + fileResponse.getFileDownloadUri() + "\" }");
    }


    @PostMapping("/test/multipartMultiple")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/test/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = null;
        try {
            resource = loadFileAsResource(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(resource);
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //  ---------------- HELPERS ----------------

    private UploadFileResponse uploadFile(MultipartFile file) throws IOException {
        String fileName = "uploaded_" + file.getOriginalFilename();
        String filePath = UPLOADED_FOLDER + "/" + fileName;
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/test/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    private final Path fileStorageLocation = Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize();


    private Resource loadFileAsResource(String fileName) throws MalformedURLException {

        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        }
        return null;
    }
}
