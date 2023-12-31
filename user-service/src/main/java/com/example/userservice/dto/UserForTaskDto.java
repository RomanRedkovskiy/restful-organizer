package com.example.userservice.dto;

public class UserForTaskDto {

    private Long id;
    private String name;

    public UserForTaskDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserForTaskDto() {

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
}
