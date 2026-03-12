package com.learning.taskmanager.controller;

import com.learning.taskmanager.dto.AuthResponse;
import com.learning.taskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<AuthResponse>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.getUser());
    }

}
