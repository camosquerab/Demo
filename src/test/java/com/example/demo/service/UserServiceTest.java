package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        String rawPassword = "password";
        String encodedPassword = "encoded-password";

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername("newuser");
        user.setPassword(encodedPassword);
        user.setRole(role);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = userService.registerUser("newuser", rawPassword, role);
        assertNotNull(registeredUser);
        assertEquals("newuser", registeredUser.getUsername());
        assertEquals(encodedPassword, registeredUser.getPassword());
        assertEquals(role, registeredUser.getRole());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("existinguser");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByUsername("existinguser");

        assertTrue(foundUser.isPresent());
        assertEquals("existinguser", foundUser.get().getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("nonexistentuser");
        });

        assertEquals("User not found with username: nonexistentuser", thrown.getMessage());
    }
}