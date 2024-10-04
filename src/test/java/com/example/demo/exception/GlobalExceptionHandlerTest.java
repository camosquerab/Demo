package com.example.demo.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleAuthenticationException() {
        AuthenticationException authException = new AuthenticationException("Usuario no autenticado") {};

        Map<String, String> response = globalExceptionHandler.handleAuthenticationException(authException);

        assertEquals("Usuario no autenticado", response.get("error"));
        assertEquals("Usuario no autenticado", response.get("message"));
    }

    @Test
    void testHandleBadCredentialsException() {
        BadCredentialsException badCredentialsException = new BadCredentialsException("Credenciales incorrectas");

        Map<String, String> response = globalExceptionHandler.handleBadCredentialsException(badCredentialsException);

        assertEquals("Credenciales incorrectas", response.get("error"));
        assertEquals("Credenciales incorrectas", response.get("message"));
    }
}