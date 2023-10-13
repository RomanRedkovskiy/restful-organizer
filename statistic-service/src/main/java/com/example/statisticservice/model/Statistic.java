package com.example.statisticservice.model;

import jakarta.persistence.*;


@Entity
public class Statistic {

    @Id
    private Long userId;
    private int completedCompilations;
    private int inProgressCompilations;
    private int uncompletedCompilations;
    private int completedTasks;
    private int inProgressTasks;
    private int uncompletedTasks;
    private boolean isDeleted;

    public Statistic(Long userId) {
        this.userId = userId;
        this.completedCompilations = 0;
        this.inProgressCompilations = 0;
        this.uncompletedCompilations = 0;
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

    public int getCompletedCompilations() {
        return completedCompilations;
    }

    public void setCompletedCompilations(int completedCompilations) {
        this.completedCompilations = completedCompilations;
    }

    public int getInProgressCompilations() {
        return inProgressCompilations;
    }

    public void setInProgressCompilations(int inProgressCompilations) {
        this.inProgressCompilations = inProgressCompilations;
    }

    public int getUncompletedCompilations() {
        return uncompletedCompilations;
    }

    public void setUncompletedCompilations(int uncompletedCompilations) {
        this.uncompletedCompilations = uncompletedCompilations;
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
}
