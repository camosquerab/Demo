package com.example.demo.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleAuthenticationException() {
        AuthenticationException ex = mock(AuthenticationException.class);
        when(ex.getMessage()).thenReturn("Invalid token");
        Map<String, String> response = globalExceptionHandler.handleAuthenticationException(ex);
        assertEquals("Usuario no autenticado", response.get("error"));
        assertEquals("Invalid token", response.get("message"));
    }

    @Test
    void testHandleBadCredentialsException() {
        BadCredentialsException ex = mock(BadCredentialsException.class);
        when(ex.getMessage()).thenReturn("Bad credentials");
        Map<String, String> response = globalExceptionHandler.handleBadCredentialsException(ex);
        assertEquals("Credenciales incorrectas", response.get("error"));
        assertEquals("Bad credentials", response.get("message"));
    }

    @Test
    void testHandleAccessDeniedException() {
        AccessDeniedException ex = mock(AccessDeniedException.class);
        when(ex.getMessage()).thenReturn("Access denied");
        Map<String, String> response = globalExceptionHandler.handleAccessDeniedException(ex);
        assertEquals("Acceso denegado", response.get("error"));
        assertEquals("Access denied", response.get("message"));
    }

    @Test
    void testHandleValidationExceptions() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(
                new FieldError("objectName", "fieldName", "Validation failed")
        ));
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        Map<String, String> response = globalExceptionHandler.handleValidationExceptions(ex);
        assertEquals(1, response.size());
        assertEquals("Validation failed", response.get("fieldName"));
    }

    @Test
    void testHandleResponseStatusException() {
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        Map<String, String> response = globalExceptionHandler.handleResponseStatusException(ex);
        assertEquals("Invalid request", response.get("message"));
    }

    @Test
    void testHandleGlobalException() {
        Exception ex = new Exception("Unexpected error");
        Map<String, String> response = globalExceptionHandler.handleGlobalException(ex);
        assertEquals("Error interno del servidor", response.get("error"));
        assertEquals("Unexpected error", response.get("message"));
    }
}