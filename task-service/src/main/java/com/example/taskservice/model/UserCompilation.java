package com.example.taskservice.model;

import com.example.taskservice.auxillary.UserCompilationId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class UserCompilation {
    @EmbeddedId
    private UserCompilationId id;
    private boolean read_only = false;
    private boolean is_shared = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compilationId")
    @JsonBackReference
    private Compilation compilation;

    public UserCompilation() {
    }

    public UserCompilation(UserCompilationId id, boolean read_only, boolean is_shared, User user, Compilation compilation) {
        this.id = id;
        this.read_only = read_only;
        this.is_shared = is_shared;
        this.user = user;
        this.compilation = compilation;
    }

    public UserCompilationId getId() {
        return id;
    }

    public void setId(UserCompilationId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Compilation getCompilation() {
        return compilation;
    }

    public void setCompilation(Compilation compilation) {
        this.compilation = compilation;
    }

    public boolean isRead_only() {
        return read_only;
    }

    public void setRead_only(boolean read_only) {
        this.read_only = read_only;
    }

    public boolean isIs_shared() {
        return is_shared;
    }

    public void setIs_shared(boolean is_shared) {
        this.is_shared = is_shared;
    }
}
