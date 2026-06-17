package org.taskmanagerapi.task.dto;

import jakarta.validation.constraints.NotNull;
import org.taskmanagerapi.task.TaskStatus;

public class UpdateTaskStatusRequest {
    @NotNull
    private TaskStatus status;

    public UpdateTaskStatusRequest (TaskStatus newStatus) {
        this.status = newStatus;
    }

    public TaskStatus getTaskStatus () {
        return this.status;
    }
}
