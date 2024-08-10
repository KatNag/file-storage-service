package com.example.filestorageservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class FileStorageServiceApplicationTests {

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        assertThat(environment.getActiveProfiles()).contains("test");
    }
}