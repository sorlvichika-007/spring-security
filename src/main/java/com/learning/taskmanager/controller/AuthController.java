package com.learning.taskmanager.controller;

import com.learning.taskmanager.dto.AuthLogin;
import com.learning.taskmanager.dto.AuthRequest;
import com.learning.taskmanager.dto.AuthResponse;
import com.learning.taskmanager.dto.JwtResponse;
import com.learning.taskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody AuthRequest authRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.createUser(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody AuthLogin authLogin){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(authLogin));
    }
}