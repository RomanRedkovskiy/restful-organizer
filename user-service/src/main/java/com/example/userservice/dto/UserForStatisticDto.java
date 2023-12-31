package com.example.userservice.dto;

import com.example.userservice.util.UserChangeMessage;

public class UserForStatisticDto {

    private String jwt;
    private Long userId;
    private String userName;
    private UserChangeMessage message;

    public UserForStatisticDto(String jwt, Long userId, String userName, UserChangeMessage message) {
        this.jwt = jwt;
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
