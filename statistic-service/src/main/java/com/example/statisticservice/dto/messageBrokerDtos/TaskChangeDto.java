package com.example.statisticservice.dto.messageBrokerDtos;

import com.example.statisticservice.util.Status;
import com.example.statisticservice.util.statisticMessagesEnum.TaskChangeMessage;

import java.util.Set;

public class TaskChangeDto {

    private Set<Long> userIds;
    private TaskChangeMessage message;
    private Status prevStatus;
    private Status currStatus;

    public TaskChangeDto(Set<Long> userIds, TaskChangeMessage message, Status prevStatus, Status currStatus) {
        this.userIds = userIds;
        this.message = message;
        this.prevStatus = prevStatus;
        this.currStatus = currStatus;
    }

    public TaskChangeDto() {
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
