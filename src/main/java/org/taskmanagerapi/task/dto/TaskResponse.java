package org.taskmanagerapi.task.dto;

import org.taskmanagerapi.task.TaskStatus;

public record TaskResponse(Long id, String title, String description, TaskStatus status) {
}
