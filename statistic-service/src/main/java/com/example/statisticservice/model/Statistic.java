package com.example.statisticservice.model;

import jakarta.persistence.*;


@Entity
public class Statistic {

    @Id
    private Long userId;
    private int completedCompilations = 0;
    private int inProgressCompilations = 0;
    private int uncompletedCompilations = 0;
    private int completedTasks = 0;
    private int inProgressTasks = 0;
    private int uncompletedTasks = 0;

    public Statistic(Long userId) {
        this.userId = userId;
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
}
