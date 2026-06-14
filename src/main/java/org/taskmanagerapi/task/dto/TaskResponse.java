package org.taskmanagerapi.task.dto;

import org.taskmanagerapi.task.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt) {
}
