package com.example.taskservice.dto.statisticDto;

import com.example.taskservice.util.statisticMessagesEnum.CompilationChangeMessage;
import com.example.taskservice.util.Status;

import java.util.List;
import java.util.Set;

public class CompilationChangeDto {
    private String jwt;
    private Set<Long> userIds;
    private CompilationChangeMessage message;
    private List<Status> statusList;

    public CompilationChangeDto(String jwt, Set<Long> userIds, CompilationChangeMessage message, List<Status> statusList) {
        this.jwt = jwt;
        this.userIds = userIds;
        this.message = message;
        this.statusList = statusList;
    }

    public CompilationChangeDto() {
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
