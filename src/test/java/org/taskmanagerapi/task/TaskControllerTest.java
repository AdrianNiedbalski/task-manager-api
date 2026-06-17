package org.taskmanagerapi.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.taskmanagerapi.task.dto.CreateTaskRequest;
import org.taskmanagerapi.task.exception.TaskNotFoundException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@DisplayName("TaskController Test")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    private static final String TITLE = "Test task";
    private static final String DESCRIPTION = "Test task description";
    private static final Long TASK_ID = 1L;

    @Test
    @DisplayName("Create Task Test")
    public void createTask_shouldReturn201AndCreatedTask() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest(TITLE, DESCRIPTION);
        Task createdTask = new Task(request.title(), request.description());

        when(taskService.create(request.title(), request.description())).thenReturn(createdTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(request.title()))
                .andExpect(jsonPath("$.description").value(request.description()));

        verify(taskService).create(request.title(), request.description());
    }

    @Test
    @DisplayName("Create Task with blank title should return 400")
    public void createTask_withBlankTitle_shouldReturn400() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest("", "Test task description");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).create(request.title(), request.description());
    }

    @Test
    @DisplayName("Find all tasks returns task list")
    public void findAllTasks_shouldReturn200AndListOfTasks() throws Exception {
        Task task1 = new Task("Test task 1", "Test task description 1");
        Task task2 = new Task("Test task 2", "Test task description 2");

        when(taskService.findAll()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(task1.getTitle()))
                .andExpect(jsonPath("$[0].description").value(task1.getDescription()))
                .andExpect(jsonPath("$[1].title").value(task2.getTitle()))
                .andExpect(jsonPath("$[1].description").value(task2.getDescription()));

        verify(taskService).findAll();
    }

    @Test
    @DisplayName("Find task by id returns task")
    public void findTaskById_shouldReturn200AndTask() throws Exception {
        Task task = new Task(TITLE, DESCRIPTION);

        when(taskService.findById(TASK_ID)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/{id}", TASK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.status").value(task.getStatus().name()));

        verify(taskService).findById(TASK_ID);

    }

    @Test
    @DisplayName("Find task by id not found returns 404")
    public void findTaskById_notFound_shouldReturn404() throws Exception {

        when(taskService.findById(TASK_ID)).thenThrow(new TaskNotFoundException(TASK_ID));

        mockMvc.perform(get("/api/tasks/{id}", TASK_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: " + TASK_ID));

        verify(taskService).findById(TASK_ID);
    }
}