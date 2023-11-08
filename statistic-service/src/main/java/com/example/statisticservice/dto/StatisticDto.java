package com.example.statisticservice.dto;

public class StatisticDto {

    private String userName;
    private int completedTasks;
    private int inProgressTasks;
    private int uncompletedTasks;
    private int overallTasks;
    private int overallCompleteness;

    public StatisticDto() {

    }

    public StatisticDto(String userName, int completedTasks, int inProgressTasks, int uncompletedTasks,
                        int overallTasks, int overallCompleteness) {
        this.userName = userName;
        this.completedTasks = completedTasks;
        this.inProgressTasks = inProgressTasks;
        this.uncompletedTasks = uncompletedTasks;
        this.overallTasks = overallTasks;
        this.overallCompleteness = overallCompleteness;
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

    public int getOverallTasks() {
        return overallTasks;
    }

    public void setOverallTasks(int overallTasks) {
        this.overallTasks = overallTasks;
    }

    public int getOverallCompleteness() {
        return overallCompleteness;
    }

    public void setOverallCompleteness(int overallCompleteness) {
        this.overallCompleteness = overallCompleteness;
    }
}
