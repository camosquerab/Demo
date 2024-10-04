package com.example.demo.config;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private JwtAuthenticationFilter jwtFilter;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationConfiguration authConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticationManagerTest() throws Exception {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        when(authConfig.getAuthenticationManager()).thenReturn(manager);

        AuthenticationManager result = securityConfig.authenticationManager(authConfig);
        assertNotNull(result);
        verify(authConfig).getAuthenticationManager();
    }

    @Test
    void authenticationProviderTest() throws Exception {
        DaoAuthenticationProvider provider = securityConfig.authenticationProvider(passwordEncoder);
        assertNotNull(provider);

        Method getUserDetailsServiceMethod = DaoAuthenticationProvider.class.getDeclaredMethod("getUserDetailsService");
        getUserDetailsServiceMethod.setAccessible(true);
        Object userDetailsService = getUserDetailsServiceMethod.invoke(provider);

        assertEquals(userService, userDetailsService);

        Method getPasswordEncoderMethod = DaoAuthenticationProvider.class.getDeclaredMethod("getPasswordEncoder");
        getPasswordEncoderMethod.setAccessible(true);
        Object passwordEncoderFromProvider = getPasswordEncoderMethod.invoke(provider);

        assertEquals(passwordEncoder, passwordEncoderFromProvider);
    }
}