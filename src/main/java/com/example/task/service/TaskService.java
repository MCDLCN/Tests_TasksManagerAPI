package com.example.task.service;

import com.example.task.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
        addTask("Learn Spring Boot");
        addTask("Write unit tests");
    }

    public Task addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        return task;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void deleteTask(UUID id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public void completeTask(UUID id) {
        tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .ifPresent(Task::complete);
    }
}