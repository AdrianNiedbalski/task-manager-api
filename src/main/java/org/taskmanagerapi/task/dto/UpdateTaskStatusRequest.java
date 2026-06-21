package org.taskmanagerapi.task.dto;

import jakarta.validation.constraints.NotNull;
import org.taskmanagerapi.task.TaskStatus;

public record UpdateTaskStatusRequest(@NotNull TaskStatus status) {
}
