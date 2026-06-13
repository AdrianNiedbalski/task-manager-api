package org.taskmanagerapi.task;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {
    protected Task (){}

    public Task (String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeStatus(TaskStatus newStatus) {
        this.status = newStatus;
    }
}
