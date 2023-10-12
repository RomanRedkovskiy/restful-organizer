package com.example.statisticservice.dto;

import com.example.statisticservice.util.statisticMessagesEnum.ChangeTaskMessage;
import com.example.statisticservice.util.Status;

public class TaskChangeDto {

    private Long userId;
    private ChangeTaskMessage message;
    private Status prevStatus;
    private Status currStatus;

    public TaskChangeDto(Long userId, ChangeTaskMessage message,
                         Status prevStatus, Status currStatus) {
        this.userId = userId;
        this.message = message;
        this.prevStatus = prevStatus;
        this.currStatus = currStatus;
    }

    public TaskChangeDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ChangeTaskMessage getMessage() {
        return message;
    }

    public void setMessage(ChangeTaskMessage message) {
        this.message = message;
    }

    public Status getPrevStatus() {
        return prevStatus;
    }

    public void setPrevStatus(Status prevStatus) {
        this.prevStatus = prevStatus;
    }

    public Status getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(Status currStatus) {
        this.currStatus = currStatus;
    }
}
