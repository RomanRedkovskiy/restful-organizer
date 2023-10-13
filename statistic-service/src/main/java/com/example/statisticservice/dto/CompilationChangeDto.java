package com.example.statisticservice.dto;

import com.example.statisticservice.util.statisticMessagesEnum.CompilationChangeMessage;
import com.example.statisticservice.util.Status;

import java.util.List;

public class CompilationChangeDto {
    private Long userId;
    private CompilationChangeMessage message;
    private List<Status> statusList;

    public CompilationChangeDto(Long userId, CompilationChangeMessage message,
                                List<Status> statusList) {
        this.userId = userId;
        this.message = message;
        this.statusList = statusList;
    }

    public CompilationChangeDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
