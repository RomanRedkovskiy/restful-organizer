package com.example.taskservice.dto;

public class UserCompilationDto {

    private String name;
    private Long compilationId;
    private Long userId;
    private boolean shared;
    private boolean readOnly;

    public UserCompilationDto() {
    }

    public UserCompilationDto(String name, Long userId, Long compilationId, boolean shared, boolean readOnly) {
        this.name = name;
        this.userId = userId;
        this.compilationId = compilationId;
        this.shared = shared;
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
        return shared;
    }

    public void setShared(boolean isShared) {
        this.shared = isShared;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
