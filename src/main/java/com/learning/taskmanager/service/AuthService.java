package com.learning.taskmanager.service;

import com.learning.taskmanager.dto.AuthLogin;
import com.learning.taskmanager.dto.AuthRequest;
import com.learning.taskmanager.dto.AuthResponse;
import com.learning.taskmanager.dto.JwtResponse;
import com.learning.taskmanager.mapper.UserMapper;
import com.learning.taskmanager.model.AppUser;
import com.learning.taskmanager.model.RoleStatus;
import com.learning.taskmanager.repository.RoleRepository;
import com.learning.taskmanager.repository.UserRepository;
import com.learning.taskmanager.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponse login(AuthLogin authLogin){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLogin.email(),
                        authLogin.password()
                )
        );
        var user = userRepository.findByEmailWithRoles(authLogin.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateToken(user);

        return new JwtResponse(accessToken);
    }

    public AuthResponse createUser(AuthRequest authRequest){
        var user = userMapper.toEntity(authRequest);
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        var role = roleRepository.findByName(RoleStatus.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));
        var saveUser = userRepository.save(user);
        return userMapper.toResponse(saveUser);
    }

    public List<AuthResponse> getUser() {
        // Before java 8 we use like this
        List<AppUser> users = userRepository.findAll();
        List<AuthResponse> responses = new ArrayList<>();
        for (var user : users)
            responses.add(userMapper.toResponse(user));
        return responses;

        // Java 8
//        List<AuthResponse> user = userRepository.findAll()
//                .stream()
//                .map(userMapper::toResponse)
//                .toList();
//        return user;
    }
}