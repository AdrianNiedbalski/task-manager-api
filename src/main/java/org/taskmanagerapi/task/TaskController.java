package org.taskmanagerapi.task;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.taskmanagerapi.task.dto.CreateTaskRequest;
import org.taskmanagerapi.task.dto.TaskResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTaskRequest createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task createTask = taskService.create(request.title(), request.description());
        return new CreateTaskRequest(createTask.getTitle(), createTask.getDescription());
    }

    @GetMapping("/all")
    public List<TaskResponse> findAll() {
        return taskService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }
}