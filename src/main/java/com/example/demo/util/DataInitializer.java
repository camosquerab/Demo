package com.example.demo.util;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            Role admin = new Role();
            admin.setName("ROLE_ADMIN");
            roleRepository.save(admin);
        }
        if (!roleRepository.existsByName("ROLE_USER")) {
            Role user = new Role();
            user.setName("ROLE_USER");
            roleRepository.save(user);
        }
    }
}