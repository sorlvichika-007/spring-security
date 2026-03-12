package com.learning.taskmanager.dto;

public record AuthRequest(
        String email,
        String password,
        String username) {
}
