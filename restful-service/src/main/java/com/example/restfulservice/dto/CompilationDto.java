package com.example.restfulservice.dto;

public class CompilationDto {

    private String name;
    private Long user_id;

    public CompilationDto() {
    }

    public CompilationDto(String name, Long user_id) {
        this.name = name;
        this.user_id = user_id;
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
