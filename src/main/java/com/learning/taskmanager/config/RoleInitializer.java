package com.learning.taskmanager.config;

import com.learning.taskmanager.model.Role;
import com.learning.taskmanager.model.RoleStatus;
import com.learning.taskmanager.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository){
        return args -> {
            if (!roleRepository.existsByName(RoleStatus.ROLE_ADMIN)){
                var admin = new Role();
                admin.setName(RoleStatus.ROLE_USER);
                roleRepository.save(admin);
            }
            if (!roleRepository.existsByName(RoleStatus.ROLE_USER)){
                var user = new Role();
                user.setName(RoleStatus.ROLE_USER);
                roleRepository.save(user);
            }
        };
    }
}
