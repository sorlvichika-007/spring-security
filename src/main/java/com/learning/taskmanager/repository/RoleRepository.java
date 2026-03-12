package com.learning.taskmanager.repository;

import com.learning.taskmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}