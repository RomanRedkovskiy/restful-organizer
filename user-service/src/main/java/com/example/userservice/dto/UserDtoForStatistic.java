package com.example.userservice.dto;

import com.example.userservice.util.ChangeUserMessage;

public class UserDtoForStatistic {

    private Long userId;
    private ChangeUserMessage message;

    public UserDtoForStatistic(Long userId, ChangeUserMessage message) {
        this.userId = userId;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ChangeUserMessage getMessage() {
        return message;
    }

    public void setMessage(ChangeUserMessage message) {
        this.message = message;
    }
}
