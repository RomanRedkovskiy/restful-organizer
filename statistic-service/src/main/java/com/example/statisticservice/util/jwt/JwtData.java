package com.example.statisticservice.util.jwt;

public class JwtData {
    Role role;
    boolean isExpired;

    public JwtData(Role role, boolean isExpired) {
        this.role = role;
        this.isExpired = isExpired;
    }

    public JwtData() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
