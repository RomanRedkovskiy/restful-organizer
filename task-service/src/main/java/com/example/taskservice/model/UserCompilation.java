package com.example.taskservice.model;

import com.example.taskservice.util.UserCompilationId;
import jakarta.persistence.*;

@Entity
@Table(name = "user_compilation")
public class UserCompilation {

    @EmbeddedId
    private UserCompilationId id;

    private boolean readOnly = false;

    private boolean isShared = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compilationId")
    private Compilation compilation;

    public UserCompilation() {
    }

    public UserCompilation(UserCompilationId id, boolean readOnly, boolean isShared, User user, Compilation compilation) {
        this.id = id;
        this.readOnly = readOnly;
        this.isShared = isShared;
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

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean read_only) {
        this.readOnly = read_only;
    }

    public boolean isIsShared() {
        return isShared;
    }

    public void setIsShared(boolean is_shared) {
        this.isShared = is_shared;
    }
}
