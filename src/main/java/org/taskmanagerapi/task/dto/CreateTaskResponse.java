package org.taskmanagerapi.task.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskResponse() {
    @NotBlank
    String title;
    String description;
}
