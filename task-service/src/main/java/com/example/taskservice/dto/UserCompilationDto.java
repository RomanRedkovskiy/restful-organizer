package com.example.taskservice.dto;

public class UserCompilationDto {

    private String name;
    private Long compilationId;
    private Long userId;
    private boolean isShared = false;
    private boolean readOnly = false;

    public UserCompilationDto() {
    }

    public UserCompilationDto(String name, Long userId, Long compilationId, boolean isShared, boolean readOnly) {
        this.name = name;
        this.userId = userId;
        this.compilationId = compilationId;
        this.isShared = isShared;
        this.readOnly = readOnly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        this.isShared = shared;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
