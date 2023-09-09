package com.example.restfulservice.dto;

public class CompilationDto {

    private Long id;
    private String name;
    private byte completeness = 0;
    private Long user_id;

    public CompilationDto() {
    }

    public CompilationDto(Long id, String name, byte completeness) {
        this.id = id;
        this.name = name;
        this.completeness = completeness;
    }

    public CompilationDto(Long id, String name, byte completeness, Long user_id) {
        this.id = id;
        this.name = name;
        this.completeness = completeness;
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

    public byte getCompleteness() {
        return completeness;
    }

    public void setCompleteness(byte completeness) {
        this.completeness = completeness;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
