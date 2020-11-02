package com.apinizer.example.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RestResourceAuthentication {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String RESPONSE_SUCCESS = "{\"result\":\"SUCCESS\",\"username\":\"username\",\"roles\":\"role1,role2,role3\" }";
    private static final String RESPONSE_FAIL = "{\"result\":\"FAIL\" }";


    @GetMapping(value = "/auth/authenticateWithHeader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticateWithHeader(HttpServletRequest request) {
        String usernameParam = request.getHeader("username");
        String passwordParam = request.getHeader("password");
        if (USERNAME.equals(usernameParam) && PASSWORD.equals(passwordParam)) {
            return ResponseEntity.ok(RESPONSE_SUCCESS);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RESPONSE_FAIL);
    }

    @GetMapping(value = "/auth/authenticateWithQueryParam", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticateWithQueryParam(@RequestParam("username") String username,
                                                             @RequestParam("password") String password) {

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return ResponseEntity.ok(RESPONSE_SUCCESS);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RESPONSE_FAIL);
    }

    @GetMapping(path = "/auth/authenticateWithPathParam/{username}/{password}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticateWithPathParam(@PathVariable("username") String username,
                                                            @PathVariable("password") String password) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return ResponseEntity.ok(RESPONSE_SUCCESS);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RESPONSE_FAIL);
    }


    @PostMapping(value = "/auth/authenticateWithBody", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticateWithBody(@RequestBody AuthUser user) {
        //{"username":"username","password":"password"}
        if (USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())) {
            return ResponseEntity.ok(RESPONSE_SUCCESS);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RESPONSE_FAIL);
    }

    @PostMapping(value = "/auth/authenticateWithForm",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticateWithForm(@RequestParam Map<String, String> paramMap) {
        //username=username&password=password
        if (USERNAME.equals(paramMap.get(USERNAME)) && PASSWORD.equals(paramMap.get(PASSWORD))) {
            return ResponseEntity.ok(RESPONSE_SUCCESS);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RESPONSE_FAIL);
    }


}
