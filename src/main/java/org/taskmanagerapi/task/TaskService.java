package org.taskmanagerapi.task;

import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description){
        Task newTask = new Task(title, description);
        return taskRepository.save(newTask);
    }
}
