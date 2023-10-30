package com.example.taskservice.dto;

public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Long compilationId;

    public TaskDto() {
    }

    public TaskDto(Long id, String title, String description, String status, Long compilationId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.compilationId = compilationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCompilationId() {
        return compilationId;
    }

    public void setCompilationId(Long compilationId) {
        this.compilationId = compilationId;
    }
}
