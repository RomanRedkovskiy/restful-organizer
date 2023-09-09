package com.example.restfulservice.dto;

public class UserCompilationDto {

    private String name;
    private Long compilation_id;
    private Long user_id;
    private boolean is_shared = false;
    private boolean read_only = false;

    public UserCompilationDto() {
    }

    public UserCompilationDto(String name, Long userId, Long compilation_id, boolean isShared, boolean readOnly) {
        this.name = name;
        this.user_id = userId;
        this.compilation_id = compilation_id;
        this.is_shared = isShared;
        this.read_only = readOnly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCompilation_id() {
        return compilation_id;
    }

    public void setCompilation_id(Long compilation_id) {
        this.compilation_id = compilation_id;
    }

    public boolean isIs_shared() {
        return is_shared;
    }

    public void setIs_shared(boolean is_shared) {
        this.is_shared = is_shared;
    }

    public boolean isRead_only() {
        return read_only;
    }

    public void setRead_only(boolean read_only) {
        this.read_only = read_only;
    }
}
