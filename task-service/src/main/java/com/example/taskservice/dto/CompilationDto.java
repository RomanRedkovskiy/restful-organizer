package com.example.taskservice.dto;

public class CompilationDto {

    private Long id;
    private String name;
    private int completeness;
    private Long userId;

    public CompilationDto() {
    }

    public CompilationDto(Long id, String name) {
        this.id = id;
        this.name = name;
        this.completeness = 0;
    }

    public CompilationDto(Long id, String name, int completeness) {
        this.id = id;
        this.name = name;
        this.completeness = completeness;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompleteness() {
        return completeness;
    }

    public void setCompleteness(int completeness) {
        this.completeness = completeness;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
