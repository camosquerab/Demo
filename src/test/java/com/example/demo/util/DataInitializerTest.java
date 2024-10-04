package com.example.demo.util;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataInitializerTest {

    @InjectMocks
    private DataInitializer dataInitializer;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRunWithExistingRoles() throws Exception {
        when(roleRepository.existsByName("ROLE_ADMIN")).thenReturn(true);
        when(roleRepository.existsByName("ROLE_USER")).thenReturn(true);
        dataInitializer.run();
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void testRunWithAdminRoleNotExists() throws Exception {
        when(roleRepository.existsByName("ROLE_ADMIN")).thenReturn(false);
        when(roleRepository.existsByName("ROLE_USER")).thenReturn(true);
        dataInitializer.run();
        verify(roleRepository).save(argThat(role -> "ROLE_ADMIN".equals(role.getName())));
    }

    @Test
    void testRunWithUserRoleNotExists() throws Exception {
        when(roleRepository.existsByName("ROLE_ADMIN")).thenReturn(true);
        when(roleRepository.existsByName("ROLE_USER")).thenReturn(false);
        dataInitializer.run();
        verify(roleRepository).save(argThat(role -> "ROLE_USER".equals(role.getName())));
    }

    @Test
    void testRunWithNoRolesExist() throws Exception {
        when(roleRepository.existsByName("ROLE_ADMIN")).thenReturn(false);
        when(roleRepository.existsByName("ROLE_USER")).thenReturn(false);
        dataInitializer.run();
        verify(roleRepository).save(argThat(role -> "ROLE_ADMIN".equals(role.getName())));
        verify(roleRepository).save(argThat(role -> "ROLE_USER".equals(role.getName())));
    }
}