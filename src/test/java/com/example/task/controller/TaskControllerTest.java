package com.example.task.controller;

import com.example.task.entity.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldReturnTasks() throws Exception {
        when(taskService.getTasks()).thenReturn(List.of(
                new Task("Learn Spring Boot"),
                new Task("Write tests")
        ));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("Learn Spring Boot"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task("New task");

        when(taskService.addTask("New task"))
                .thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType("text/plain")
                        .content("New task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("New task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{taskId}", UUID.randomUUID()))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(any(UUID.class));
    }

    @Test
    void shouldCompleteTask() throws Exception {
        mockMvc.perform(put("/tasks/{taskId}/complete", UUID.randomUUID()))
                .andExpect(status().isNoContent());

        verify(taskService).completeTask(any(UUID.class));
    }


}