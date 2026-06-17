package org.taskmanagerapi.task;

import org.springframework.stereotype.Service;
import org.taskmanagerapi.task.exception.TaskNotFoundException;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(String title, String description) {
        Task newTask = new Task(title, description);
        return taskRepository.save(newTask);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
