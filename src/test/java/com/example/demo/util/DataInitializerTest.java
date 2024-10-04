package com.example.demo.util;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DataInitializerTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ApplicationContext applicationContext;

    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {
        roleRepository.deleteAll(); // Clear the repository before each test
        dataInitializer = applicationContext.getBean(DataInitializer.class);
    }

    @Test
    public void testRoleInitialization() throws Exception {
        // Act: run the DataInitializer
        dataInitializer.run();

        // Assert: check that the roles are created
        assertThat(roleRepository.count()).isEqualTo(2);
        assertThat(roleRepository.existsByName("ROLE_ADMIN")).isTrue();
        assertThat(roleRepository.existsByName("ROLE_USER")).isTrue();
    }

    @Test
    public void testRoleInitialization_WhenRolesExist() throws Exception {
        // Arrange: set up existing roles
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        // Act: run the DataInitializer
        dataInitializer.run();

        // Assert: check that only one role is created
        assertThat(roleRepository.count()).isEqualTo(2); // ROLE_ADMIN and ROLE_USER should be present
    }
}