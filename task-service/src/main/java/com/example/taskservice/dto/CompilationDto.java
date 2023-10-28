package com.example.taskservice.dto;

public class CompilationDto {

    private Long id;
    private String name;
    private Long user_id;

    public CompilationDto() {
    }

    public CompilationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CompilationDto(Long id, String name, byte completeness, Long user_id) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
