package com.example.taskservice.dto.statisticDto;

import com.example.taskservice.util.statisticMessagesEnum.TaskChangeMessage;
import com.example.taskservice.util.Status;

public class TaskChangeDto {

    private Long userId;
    private TaskChangeMessage message;
    private Status prevStatus;
    private Status currStatus;

    public TaskChangeDto(Long userId, TaskChangeMessage message,
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

    public TaskChangeMessage getMessage() {
        return message;
    }

    public void setMessage(TaskChangeMessage message) {
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
