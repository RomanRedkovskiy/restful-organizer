package com.example.statisticservice.dto;

import com.example.statisticservice.util.statisticMessagesEnum.UserChangeMessage;

public class UserChangeDto {

    private Long userId;
    private UserChangeMessage message;

    public UserChangeDto(Long userId, UserChangeMessage message) {
        this.userId = userId;
        this.message = message;
    }

    public UserChangeDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserChangeMessage getMessage() {
        return message;
    }

    public void setMessage(UserChangeMessage message) {
        this.message = message;
    }
}
