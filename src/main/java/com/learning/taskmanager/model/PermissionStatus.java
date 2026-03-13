package com.learning.taskmanager.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionStatus {
    TASK_READ("task:read"),
    TASK_WRITE("task:write");

    private final String description;

}
