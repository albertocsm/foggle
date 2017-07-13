package com.github.albertocsm.foggle.bll.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

import java.util.UUID;

public class Toggle extends ResourceSupport {

    @JsonIgnore
    private UUID resourceId;

    @NotNull
    private String description;

    @NotNull
    private boolean active;

    @NotNull
    private boolean global;

    private Long updatedAt;

    @Override
    public String toString() {
        return "Toggle{" +
            "resourceId=" + resourceId +
            ", description='" + description + '\'' +
            ", active=" + active +
            ", global=" + global +
            ", updatedAt=" + updatedAt +
            '}';
    }

    public String getDescription() {
        return description;
    }

    public Toggle setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Toggle setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isGlobal() {
        return global;
    }

    public Toggle setGlobal(boolean global) {
        this.global = global;
        return this;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Toggle setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public Toggle setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
        return this;
    }
}
