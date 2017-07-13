package com.github.albertocsm.foggle.bll;

import com.github.albertocsm.foggle.bll.exception.NotFoundException;
import com.github.albertocsm.foggle.bll.dto.Toggle;
import com.github.albertocsm.foggle.bll.dto.ToggleCriteria;

import java.util.Collection;
import java.util.UUID;

public interface ToggleService {

    UUID createToggle(Toggle value);

    void updateToggle(UUID id, Toggle value);

    void deleteToggle(UUID id);

    Toggle findToggle(UUID id) throws NotFoundException;

    Collection<Toggle> filterToggle(ToggleCriteria criteria);
}
