package com.learning.taskmanager.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Set;
import static com.learning.taskmanager.model.PermissionStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleStatus {
    ROLE_USER(Set.of(TASK_READ)),
    ROLE_ADMIN(Set.of(TASK_READ,TASK_WRITE));

    private final Set<PermissionStatus> permission;

}
