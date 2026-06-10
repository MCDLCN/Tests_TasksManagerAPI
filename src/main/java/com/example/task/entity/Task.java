package com.example.task.entity;

import java.util.UUID;

public class Task {
    private UUID id;
    private String description;
    private boolean completed;

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.completed = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void complete() {
        this.completed = true;
    }
}
