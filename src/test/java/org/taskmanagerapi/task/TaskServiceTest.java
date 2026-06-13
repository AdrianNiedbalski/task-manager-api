package org.taskmanagerapi.task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Test")
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
    }


    @DisplayName("Create Task Test")
    void createTaskTest() {
        String title = "Test task";
        String description = "Test task description";
        Task task = taskService.create(title, description);

        when(taskService.create(title, description)).thenReturn(task);

        assertNotNull(task);
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
    }
}