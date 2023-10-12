package com.example.statisticservice.dto;

public class UserChangeDto {

    private Long userId;

    public UserChangeDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
