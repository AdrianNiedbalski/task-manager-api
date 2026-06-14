package org.taskmanagerapi.task;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.taskmanagerapi.task.dto.CreateTaskRequest;
import org.taskmanagerapi.task.dto.TaskResponse;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    @Valid
    @RequestBody CreateTaskRequest request
    public CreateTaskRequest createTask(CreateTaskRequest request) {
        Task createTask = taskService.create(request.title(), request.description());
        return new CreateTaskRequest(createTask.getTitle(), createTask.getDescription());
    }

    @GetMapping("/all")
    public Task findAll() {
        return taskService.findAll();
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
    }
}
