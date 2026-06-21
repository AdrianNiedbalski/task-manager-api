package org.taskmanagerapi.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.taskmanagerapi.task.exception.TaskNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Test")
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private static final String TITLE = "Test task";
    private static final String DESCRIPTION = "Test task description";

    @Test
    @DisplayName("Create Task Test")
    void createTaskTest() {

        Task savedTask = new Task(TITLE, DESCRIPTION);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.create(TITLE, DESCRIPTION);

        assertNotNull(result);
        assertEquals(TITLE, result.getTitle());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(TaskStatus.TODO, result.getStatus());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Find All Tasks Test")
    void findAllTasksTest() {
        Task task1 = new Task(TITLE, DESCRIPTION);
        Task task2 = new Task("Another task", "Another description");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TITLE, result.get(0).getTitle());
        assertEquals("Another task", result.get(1).getTitle());
        verify(taskRepository).findAll();
    }

    @Test
    @DisplayName("Find Task By Id Test")
    void findTaskByIdTest() {
        Task task = new Task(TITLE, DESCRIPTION);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);

        assertNotNull(result);
        assertEquals(TITLE, result.getTitle());
        assertEquals(DESCRIPTION, result.getDescription());
        verify(taskRepository).findById(1L);
    }

    @Test
    @DisplayName("Find Task By Id Not Found Test")
    void findTaskByIdNotFoundTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.findById(1L);
        });

        assertEquals("Task not found with id: 1", exception.getMessage());
        verify(taskRepository).findById(1L);
    }

    @Test
    @DisplayName("Change status task and save")
    void changeStatus_shouldChangeTaskStatusAndSaveTask() {
        Long id = 1L;
        TaskStatus newStatus = TaskStatus.DONE;

        Task task = new Task(TITLE, DESCRIPTION);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.changeStatus(id, newStatus);

        assertEquals(TaskStatus.DONE, result.getStatus());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("Change status task does nott exist")
    void changeStatus_shouldThrowExceptionWhenTaskDoesNotExist() {
        Long id = 1L;
        TaskStatus newStatus = TaskStatus.DONE;

        Task task = new Task(TITLE, DESCRIPTION);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.changeStatus(id, newStatus));

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.changeStatus(id, newStatus));
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).save(any(Task.class));
    }
}