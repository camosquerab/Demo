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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testLogin_Success() {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("testpass");

        User user = new User();
        user.setUsername("testuser");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user)).thenReturn("token");

        ResponseEntity<AuthResponse> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody().getToken());
    }

    @Test
    void testLogin_Failure_BadCredentials() {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales incorrectas"));

        try {
            authController.login(request);
        } catch (BadCredentialsException ex) {
            assertEquals("Credenciales incorrectas", ex.getMessage());
        }
    }

    @Test
    void testRegister_Success() {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("testpass");

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername("testuser");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userService.registerUser("testuser", "testpass", role)).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("token");

        ResponseEntity<AuthResponse> response = authController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody().getToken());
    }
}