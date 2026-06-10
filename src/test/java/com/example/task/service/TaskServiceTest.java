package com.example.task.service;

import com.example.task.entity.Task;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();

        Task task = service.addTask("Buy milk");

        assertEquals(1, service.getTasks().size());
        assertEquals("Buy milk", task.getDescription());
    }

    @Test
    void newTaskShouldNotBeCompleted() {
        TaskService service = new TaskService();

        Task task = service.addTask("Buy milk");

        assertFalse(task.isCompleted());
    }

    @Test
    void shouldCompleteTask() {
        TaskService service = new TaskService();

        Task task = service.addTask("Buy milk");

        service.completeTask(task.getId());

        assertTrue(task.isCompleted());
    }

    @Test
    void shouldDeleteTask() {
        TaskService service = new TaskService();
        Task task = service.addTask("Buy milk");

        service.deleteTask(task.getId());

        assertTrue(service.getTasks().isEmpty());
    }

    @Test
    void shouldDoNothingWhenCompletingUnknownTask() {
        TaskService service = new TaskService();

        assertDoesNotThrow(() -> service.completeTask(UUID.randomUUID()));
    }
}