package com.example.userservice.dto;

public class UserDtoForStatistic {

    private Long id;

    public UserDtoForStatistic(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
