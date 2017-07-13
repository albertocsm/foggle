package com.github.albertocsm.foggle.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;

@Entity
public class ToggleReferenceEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "toggle_id")
    private UUID toggleId;

    @Column(name = "sys_id")
    private String sysId;

    @Column(name = "sys_version")
    private String sysVersion;

    @Column(name = "active")
    private boolean active;

    @Column(name = "updated_at")
    private Long updatedAt;

    public UUID getId() {
        return id;
    }

    public ToggleReferenceEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public ToggleReferenceEntity setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getSysId() {
        return sysId;
    }

    public ToggleReferenceEntity setSysId(String sysId) {
        this.sysId = sysId;
        return this;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public ToggleReferenceEntity setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ToggleReferenceEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public UUID getToggleId() {
        return toggleId;
    }

    public ToggleReferenceEntity setToggleId(UUID toggleId) {
        this.toggleId = toggleId;
        return this;
    }
}
