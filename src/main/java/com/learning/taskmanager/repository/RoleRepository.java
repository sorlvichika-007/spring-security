package com.learning.taskmanager.repository;

import com.learning.taskmanager.model.Role;
import com.learning.taskmanager.model.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleStatus name);

    boolean existsByName(RoleStatus name);
}