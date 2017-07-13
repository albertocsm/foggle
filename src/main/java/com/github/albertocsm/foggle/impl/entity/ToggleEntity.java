package com.github.albertocsm.foggle.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;

@Entity
public class ToggleEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;

    @Column(name = "global")
    private boolean global;

    @Column(name = "updated_at")
    private Long updatedAt;

    public UUID getId() {
        return id;
    }

    public ToggleEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ToggleEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ToggleEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isGlobal() {
        return global;
    }

    public ToggleEntity setGlobal(boolean global) {
        this.global = global;
        return this;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public ToggleEntity setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
