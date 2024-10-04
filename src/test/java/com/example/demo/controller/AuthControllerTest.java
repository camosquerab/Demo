package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        User user = new User();
        user.setUsername("testuser");
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user)).thenReturn("test-token");
        ResponseEntity<AuthResponse> response = authController.login(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test-token", response.getBody().getToken());
    }

    @Test
    void testLoginFailure() {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");
        doThrow(new BadCredentialsException("Credenciales incorrectas"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        BadCredentialsException thrown = assertThrows(BadCredentialsException.class, () -> {
            authController.login(request);
        });
        assertEquals("Credenciales incorrectas", thrown.getMessage());
    }

    @Test
    void testRegisterSuccess() {
        AuthRequest request = new AuthRequest();
        request.setUsername("newuser");
        request.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername("newuser");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userService.registerUser("newuser", "password", role)).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("new-token");
        ResponseEntity<AuthResponse> response = authController.register(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("new-token", response.getBody().getToken());
    }

    @Test
    void testRegisterRoleNotFound() {
        AuthRequest request = new AuthRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            authController.register(request);
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, thrown.getStatusCode());
        assertEquals("No se encontró el rol USER", thrown.getReason());
    }
}