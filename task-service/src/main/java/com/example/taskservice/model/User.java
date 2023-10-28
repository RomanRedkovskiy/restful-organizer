package com.example.taskservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    private Long id;

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<UserCompilation> compilations = new HashSet<>();

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
}
