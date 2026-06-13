package org.taskmanagerapi.task;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Task {
    public Task () {
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue
    Long id;

    String title;

    String description;

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    LocalDateTime createdAt;
}
