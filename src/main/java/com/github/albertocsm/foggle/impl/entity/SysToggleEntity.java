package com.github.albertocsm.foggle.impl.entity;

import com.github.albertocsm.foggle.bll.dto.ToggleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SysToggleEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "record_type")
    private ToggleType type;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;

    public ToggleType getType() {
        return type;
    }

    public SysToggleEntity setType(ToggleType type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SysToggleEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public SysToggleEntity setActive(boolean active) {
        this.active = active;
        return this;
    }
}
