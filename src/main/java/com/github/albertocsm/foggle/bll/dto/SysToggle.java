package com.github.albertocsm.foggle.bll.dto;

import org.springframework.hateoas.ResourceSupport;

public class SysToggle extends ResourceSupport {

    private String description;

    private boolean active;

    @Override
    public String toString() {
        return "SysToggle{" +
            "description='" + description + '\'' +
            ", active=" + active +
            '}';
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public SysToggle setDescription(String description) {
        this.description = description;
        return this;
    }

    public SysToggle setActive(boolean active) {
        this.active = active;
        return this;
    }
}
