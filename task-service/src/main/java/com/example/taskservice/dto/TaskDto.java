package com.example.taskservice.dto;

public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Long compilation_id;

    public TaskDto() {
    }

    public TaskDto(Long id, String title, String description, String status, Long compilation_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.compilation_id = compilation_id;
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

    public Long getCompilation_id() {
        return compilation_id;
    }

    public void setCompilation_id(Long compilation_id) {
        this.compilation_id = compilation_id;
    }
}
