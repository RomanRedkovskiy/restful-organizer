package com.example.statisticservice.model;

import jakarta.persistence.*;

import com.example.statisticservice.util.Status;

@Entity
public class Statistic {

    @Id
    private Long userId;
    private String userName;
    private int completedTasks;
    private int inProgressTasks;
    private int uncompletedTasks;
    private boolean isDeleted;

    public Statistic(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.completedTasks = 0;
        this.inProgressTasks = 0;
        this.uncompletedTasks = 0;
        this.isDeleted = false;
    }

    public Statistic(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getInProgressTasks() {
        return inProgressTasks;
    }

    public void setInProgressTasks(int inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }

    public int getUncompletedTasks() {
        return uncompletedTasks;
    }

    public void setUncompletedTasks(int uncompletedTasks) {
        this.uncompletedTasks = uncompletedTasks;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void incrementTaskCount(Status status) {
        switch (status) {
            case COMPLETED -> this.completedTasks++;
            case IN_PROGRESS -> this.inProgressTasks++;
            case UNCOMPLETED -> this.uncompletedTasks++;
        }
    }
    public void decrementTaskCount(Status status) {
        switch (status) {
            case COMPLETED -> this.completedTasks--;
            case IN_PROGRESS -> this.inProgressTasks--;
            case UNCOMPLETED -> this.uncompletedTasks--;
        }
    }
}
