package com.github.albertocsm.foggle.bll.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

import java.util.UUID;

public class ToggleReference extends ResourceSupport {

    private UUID resourceId;

    @JsonIgnore
    private UUID toggleId;

    @NotNull
    private String sysId;

    @NotNull
    private String sysVersion;

    @NotNull
    private boolean active;

    private Long updatedAt;

    @Override
    public String toString() {
        return "ToggleReference{" +
            "resourceId=" + resourceId +
            ", toggleId=" + toggleId +
            ", sysId='" + sysId + '\'' +
            ", sysVersion='" + sysVersion + '\'' +
            ", active=" + active +
            ", updatedAt=" + updatedAt +
            '}';
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public ToggleReference setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public UUID getToggleId() {
        return toggleId;
    }

    public ToggleReference setToggleId(UUID toggleId) {
        this.toggleId = toggleId;
        return this;
    }

    public String getSysId() {
        return sysId;
    }

    public ToggleReference setSysId(String sysId) {
        this.sysId = sysId;
        return this;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public ToggleReference setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ToggleReference setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public ToggleReference setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
