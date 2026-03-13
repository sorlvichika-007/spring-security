package com.learning.taskmanager.repository;

import com.learning.taskmanager.model.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<AppUser> findByEmail(String email);

    @Query("select u from AppUser u join fetch u.roles where u.email = :email")
    Optional<AppUser> findByEmailWithRoles(String email);
}
