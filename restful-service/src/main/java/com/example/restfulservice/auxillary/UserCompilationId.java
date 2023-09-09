package com.example.restfulservice.auxillary;

import java.io.Serializable;

public class UserCompilationId implements Serializable {

    private Long userId;
    private Long compilationId;

    public UserCompilationId() {
    }

    public UserCompilationId(Long userId, Long compilationId) {
        this.userId = userId;
        this.compilationId = compilationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompilationId() {
        return compilationId;
    }

    public void setCompilationId(Long compilationId) {
        this.compilationId = compilationId;
    }

}
