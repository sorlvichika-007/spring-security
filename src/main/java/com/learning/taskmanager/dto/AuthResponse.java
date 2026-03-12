package com.learning.taskmanager.dto;

import java.util.Set;

public record AuthResponse(
        Long id,
        String username,
        String email,
        Set<String> roles
) {
}