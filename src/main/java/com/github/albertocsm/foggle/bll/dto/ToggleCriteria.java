package com.github.albertocsm.foggle.bll.dto;

import java.io.Serializable;

public class ToggleCriteria implements Serializable {

    private Boolean active;

    private Boolean global;

    @Override
    public String toString() {
        return "ToggleCriteria{" +
            "active=" + active +
            ", global=" + global +
            '}';
    }

    public Boolean getActive() {
        return active;
    }

    public ToggleCriteria setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Boolean getGlobal() {
        return global;
    }

    public ToggleCriteria setGlobal(Boolean global) {
        this.global = global;
        return this;
    }
}
