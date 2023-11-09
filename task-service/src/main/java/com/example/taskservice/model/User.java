package com.example.taskservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private Set<UserCompilation> compilations = new HashSet<>();

    boolean isAdmin = false;

    boolean isDeleted = false;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserCompilation> getCompilations() {
        return compilations;
    }

    public void setCompilations(Set<UserCompilation> compilations) {
        this.compilations = compilations;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

