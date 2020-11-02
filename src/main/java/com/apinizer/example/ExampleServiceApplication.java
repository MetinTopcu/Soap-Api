package com.apinizer.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.apinizer.example")
public class ExampleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleServiceApplication.class, args);
    }

    @Bean
    public InMemoryHttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
