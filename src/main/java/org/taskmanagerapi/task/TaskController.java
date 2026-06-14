package org.taskmanagerapi.task;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.taskmanagerapi.task.dto.CreateTaskResponse;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("api/tasks/create")
    public CreateTaskResponse createTask(String title, String description) {
        Task createTask = taskService.create(title, description);
        return new CreateTaskResponse(createTask.getTitle(), createTask.getDescription());
    }
}
