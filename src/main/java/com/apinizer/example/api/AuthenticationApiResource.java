package com.apinizer.example.api;

import com.apinizer.example.api.rest.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;

@RestController
public class AuthenticationApiResource {
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "P@ssW@rd!";
    private static final String RESPONSE_SUCCESS = "{\"result\":\"SUCCESS\",\"username\":\"user1\",\"roles\":[\"account\",\"finance\",\"hr\"] }";
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

    @GetMapping(value = "/auth/authenticateWithBase64", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> authenticateWithBase64(HttpServletRequest request) {
        String tokenStr = request.getHeader("Token");
        tokenStr = StringUtils.removeStart(tokenStr, "Basic ");
        tokenStr = StringUtils.removeStart(tokenStr, "Bearer ");
        String tokenArr[] = new String(Base64.getDecoder().decode(tokenStr)).split(":");

        if (USERNAME.equals(tokenArr[0]) && PASSWORD.equals(tokenArr[1])) {
            return ResponseEntity.ok(RESPONSE_SUCCESS);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RESPONSE_FAIL);
    }


    @GetMapping(value = "/apinizer/convertBase64", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> convertBase64(HttpServletRequest request) {
        String authz = request.getHeader("Token");
        String[] split_string = authz.split("\\.");
        String base64EncodedBody = split_string[1];
        String jwtTokenBody = new String(Base64.getDecoder().decode(base64EncodedBody));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtTokenBody);
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
    public ResponseEntity<String> authenticateWithBody(@RequestBody User user) {
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
