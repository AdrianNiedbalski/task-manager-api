package org.taskmanagerapi.task;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.taskmanagerapi.task.dto.CreateTaskResponse;
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
    @RequestBody
    public CreateTaskResponse createTask(String title, String description) {
        Task createTask = taskService.create(title, description);
        return new CreateTaskResponse(request.getTitle(), request.getDescription());
    }

    @GetMapping("/all")
    public Task findAll() {
        return taskService.findAll();
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
    }
}
