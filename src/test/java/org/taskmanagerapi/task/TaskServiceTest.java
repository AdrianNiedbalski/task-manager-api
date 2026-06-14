package org.taskmanagerapi.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    @DisplayName("Create Task Test")
    void createTaskTest() {
        String title = "Test task";
        String description = "Test task description";

        Task savedTask = new Task(title, description);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.create(title, description);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());

        verify(taskRepository).save(any(Task.class));
    }
}