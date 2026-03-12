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
            if (!roleRepository.existsByName(RoleStatus.ADMIN_ROLE)){
                var admin = new Role();
                admin.setName(RoleStatus.ADMIN_ROLE);
                roleRepository.save(admin);
            }
            if (!roleRepository.existsByName(RoleStatus.USER_ROLE)){
                var user = new Role();
                user.setName(RoleStatus.USER_ROLE);
                roleRepository.save(user);
            }
        };
    }
}
