package com.example.statisticservice.dto;

import com.example.statisticservice.util.Status;
import com.example.statisticservice.util.statisticMessagesEnum.CompilationChangeMessage;

import java.util.List;
import java.util.Set;

public class CompilationChangeDto {
    private Set<Long> userIds;
    private CompilationChangeMessage message;
    private List<Status> statusList;

    public CompilationChangeDto(Set<Long> userIds, CompilationChangeMessage message, List<Status> statusList) {
        this.userIds = userIds;
        this.message = message;
        this.statusList = statusList;
    }

    public CompilationChangeDto() {
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public CompilationChangeMessage getMessage() {
        return message;
    }

    public void setMessage(CompilationChangeMessage message) {
        this.message = message;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }
}
