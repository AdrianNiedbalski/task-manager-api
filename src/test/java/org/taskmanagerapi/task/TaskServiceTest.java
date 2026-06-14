package org.taskmanagerapi.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Test")
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private static final String title = "Test task";
    private static final String description = "Test task description";


    @Test
    @DisplayName("Create Task Test")
    void createTaskTest() {

        Task savedTask = new Task(title, description);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.create(title, description);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(TaskStatus.TODO, result.getStatus());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Find All Tasks Test")
    void findAllTasksTest() {
        Task task1 = new Task(title, description);
        Task task2 = new Task("Another task", "Another description");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(title, result.get(0).getTitle());
        assertEquals("Another task", result.get(1).getTitle());
        verify(taskRepository).findAll();
    }
}