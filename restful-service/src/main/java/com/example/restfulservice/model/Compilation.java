package com.example.restfulservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "compilation")
    private Set<Task> tasks = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "compilations")
    private Set<User> users = new HashSet<>();

    private String name;
    private byte completeness;
    private boolean isDeleted = false;

    public Compilation(Long id, String name, byte completeness) {
        this.id = id;
        this.name = name;
        this.completeness = completeness;
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

    public byte getCompleteness() {
        return completeness;
    }

    public void setCompleteness(byte completeness) {
        this.completeness = completeness;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Compilation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", completeness=" + completeness +
                '}';
    }
}
