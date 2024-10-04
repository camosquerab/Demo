package com.example.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @InjectMocks
    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void passwordEncoderTest() {
        PasswordEncoder passwordEncoder = appConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void addCorsMappingsTest() {
        CorsRegistry registry = new CorsRegistry();
        appConfig.addCorsMappings(registry);
        assertNotNull(registry);
    }
}