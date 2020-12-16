package com.apinizer.example.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApiResource {

    public static final Logger logger = LoggerFactory.getLogger(TestApiResource.class);

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


}
