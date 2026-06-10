package com.example.task.controller;

import com.example.task.entity.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Test
    void shouldReturnTasksFromRealService() throws Exception {
        taskService.addTask("Integration test task");

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty())
                .andExpect(jsonPath("$[0].description").exists());
    }

    @Test
    void shouldCreateTaskInRealService() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType("text/plain")
                        .content("Integration task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Integration task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldDeleteTaskFromRealService() throws Exception {
        Task task = taskService.addTask("Task to delete");

        mockMvc.perform(delete("/tasks/{taskId}", task.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCompleteTaskInRealService() throws Exception {
        Task task = taskService.addTask("Task to complete");

        mockMvc.perform(put("/tasks/{taskId}/complete", task.getId()))
                .andExpect(status().isNoContent());

        assertTrue(task.isCompleted());
    }
}