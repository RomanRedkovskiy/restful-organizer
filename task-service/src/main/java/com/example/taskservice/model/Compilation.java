package com.example.taskservice.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "compilation")
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "compilation")
    private Set<UserCompilation> users = new HashSet<>();

    private String name;

    private int completeness;

    private boolean isDeleted = false;

    public Compilation(Long id, String name) {
        this.id = id;
        this.name = name;
        this.completeness = 0;
    }

    public Compilation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public int getCompleteness() {
        return completeness;
    }

    public void setCompleteness(int completeness) {
        this.completeness = completeness;
    }

    public Set<UserCompilation> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCompilation> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Compilation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
