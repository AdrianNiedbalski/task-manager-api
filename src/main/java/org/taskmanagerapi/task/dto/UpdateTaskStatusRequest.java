package org.taskmanagerapi.task.dto;

import jakarta.validation.constraints.NotNull;
import org.taskmanagerapi.task.TaskStatus;

public record UpdateTaskStatusRequest (@NotNull  TaskStatus status) {

    public UpdateTaskStatusRequest (TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getTaskStatus () {
        return this.status;
    }
}
