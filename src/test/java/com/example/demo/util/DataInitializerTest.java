package com.example.demo.util;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DataInitializerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    public DataInitializerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRun_CreatesRolesIfNotExist() throws Exception {
        when(roleRepository.existsByName("ROLE_ADMIN")).thenReturn(false);
        when(roleRepository.existsByName("ROLE_USER")).thenReturn(false);

        dataInitializer.run();

        verify(roleRepository, times(1)).save(argThat(role -> role.getName().equals("ROLE_ADMIN")));
        verify(roleRepository, times(1)).save(argThat(role -> role.getName().equals("ROLE_USER")));
    }

    @Test
    void testRun_RolesAlreadyExist() throws Exception {
        when(roleRepository.existsByName("ROLE_ADMIN")).thenReturn(true);
        when(roleRepository.existsByName("ROLE_USER")).thenReturn(true);

        dataInitializer.run();

        verify(roleRepository, never()).save(any());
    }
}