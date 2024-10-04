package com.example.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        roleRepository.deleteAll();
    }

    @Test
    public void testExistsByNameReturnsFalseForNonExistentRole() {
        boolean exists = roleRepository.existsByName("ROLE_NON_EXISTENT");
        assertThat(exists).isFalse();
    }
}