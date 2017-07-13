package com.github.albertocsm.foggle.bll;

import com.github.albertocsm.foggle.bll.dto.ToggleReference;

import java.util.Collection;
import java.util.UUID;

public interface ToggleReferenceService {

    Collection<UUID> createToggleRef(UUID toggleId, Collection<ToggleReference> value);

    void updateToggleRef(UUID toggleId, Collection<ToggleReference> value);

    void deleteToggleRef(UUID toggleId, Collection<UUID> value);

    ToggleReference findToggleRef(UUID toggleId, UUID id);

    Collection<ToggleReference> filterToggleRef(UUID toggleId);
}
