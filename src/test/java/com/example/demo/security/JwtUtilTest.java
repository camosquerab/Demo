package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private User user;

    @Value("${jwt.secret}")
    private String secret;

    @BeforeEach
    void setUp() {
        jwtUtil.init(); // Initialize the key after loading the secret
    }

    @Test
    void testGenerateToken_Success() {
        when(user.getUsername()).thenReturn("testuser");
        String token = jwtUtil.generateToken(user);

        assertNotNull(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtUtil.getKey()).build().parseClaimsJws(token).getBody();
        assertEquals("testuser", claims.getSubject());
    }

    @Test
    void testValidateToken_Success() {
        String token = jwtUtil.generateToken(user);
        when(user.getUsername()).thenReturn("testuser");

        boolean isValid = jwtUtil.validateToken(token, user);

        assertTrue(isValid);
    }

    @Test
    void testExtractUsername_Success() {
        when(user.getUsername()).thenReturn("testuser");
        String token = jwtUtil.generateToken(user);

        String username = jwtUtil.extractUsername(token);

        assertEquals("testuser", username);
    }
}