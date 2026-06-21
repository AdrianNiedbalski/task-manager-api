package org.taskmanagerapi.task;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.taskmanagerapi.task.dto.CreateTaskRequest;
import org.taskmanagerapi.task.dto.TaskResponse;
import org.taskmanagerapi.task.dto.UpdateTaskStatusRequest;
import org.taskmanagerapi.task.exception.TaskNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task createTask = taskService.create(request.title(), request.description());
        return toResponse(createTask);
    }

    @GetMapping
    public List<TaskResponse> findAll() {
        return taskService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse findTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return toResponse(task);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse changeStatus(@PathVariable Long id, @Valid @RequestBody UpdateTaskStatusRequest request) {
        Task task = taskService.changeStatus(id, request.status());

        return toResponse(task);
    }

    @DeleteMapping("/api/tasks/{id}")
    public void deleteTask(Long id) {
        Task taskToDelete = taskService.findById(id);
        taskRepository.delete(taskToDelete);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }


}