package com.learning.taskmanager.mapper;

import com.learning.taskmanager.dto.AuthRequest;
import com.learning.taskmanager.dto.AuthResponse;
import com.learning.taskmanager.model.AppUser;
import com.learning.taskmanager.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AppUser toEntity(AuthRequest authRequest);

    @Mapping(target = "roles" , source = "roles")
    AuthResponse toResponse(AppUser user);

    default Set<String> mapRoles(Set<Role> roles){
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
