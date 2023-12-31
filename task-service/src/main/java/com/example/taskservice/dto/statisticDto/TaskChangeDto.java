package com.example.taskservice.dto.statisticDto;

import com.example.taskservice.util.statisticMessagesEnum.TaskChangeMessage;
import com.example.taskservice.util.Status;

import java.util.Set;

public class TaskChangeDto{

    private String jwt;
    private Set<Long> userIds;
    private TaskChangeMessage message;
    private Status prevStatus;
    private Status currStatus;

    public TaskChangeDto(String jwt, Set<Long> userIds, TaskChangeMessage message, Status prevStatus, Status currStatus) {
        this.jwt = jwt;
        this.userIds = userIds;
        this.message = message;
        this.prevStatus = prevStatus;
        this.currStatus = currStatus;
    }

    public TaskChangeDto() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
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
