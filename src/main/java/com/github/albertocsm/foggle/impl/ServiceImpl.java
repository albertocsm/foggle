package com.github.albertocsm.foggle.impl;

import com.github.albertocsm.foggle.impl.entity.SysToggleEntity;
import com.github.albertocsm.foggle.impl.entity.ToggleEntity;
import com.github.albertocsm.foggle.impl.entity.ToggleReferenceEntity;
import com.github.albertocsm.foggle.impl.mapper.SysToggleMapper;
import com.github.albertocsm.foggle.impl.mapper.ToggleMapper;
import com.github.albertocsm.foggle.impl.mapper.ToggleReferenceMapper;
import com.github.albertocsm.foggle.impl.dal.SysToggleRepository;
import com.github.albertocsm.foggle.impl.dal.ToggleReferenceRepository;
import com.github.albertocsm.foggle.impl.dal.ToggleRepository;
import com.github.albertocsm.foggle.bll.dto.SysToggle;
import com.github.albertocsm.foggle.bll.dto.Toggle;
import com.github.albertocsm.foggle.bll.dto.ToggleCriteria;
import com.github.albertocsm.foggle.bll.dto.ToggleReference;
import com.github.albertocsm.foggle.bll.dto.ToggleType;
import com.github.albertocsm.foggle.bll.SysToggleService;
import com.github.albertocsm.foggle.bll.ToggleReferenceService;
import com.github.albertocsm.foggle.bll.ToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements SysToggleService, ToggleReferenceService, ToggleService {

    private ToggleRepository toggleRepo;
    private ToggleReferenceRepository toggleRefRepo;
    private SysToggleRepository sysToggleRepo;

    @Autowired
    public ServiceImpl(
        ToggleRepository toggleRepo,
        ToggleReferenceRepository toggleRefRepo,
        SysToggleRepository sysToggleRepo) {

        this.toggleRepo = toggleRepo;
        this.toggleRefRepo = toggleRefRepo;
        this.sysToggleRepo = sysToggleRepo;
    }

    @Override
    public UUID createToggle(final Toggle value) {

        return toggleRepo.create(ToggleMapper.toEntity.apply(value));
    }

    @Override
    public void updateToggle(final UUID id, final Toggle value) {

        toggleRepo.update(id, ToggleMapper.toEntity.apply(value));
    }

    @Override
    public void deleteToggle(final UUID id) {

        toggleRepo.delete(id);
    }

    @Override
    public Toggle findToggle(final UUID id) {

        ToggleEntity toggleEntity = toggleRepo.find(id);
        return ToggleMapper.toDto.apply(toggleEntity);
    }

    @Override
    public Collection<Toggle> filterToggle(final ToggleCriteria criteria) {
        return toggleRepo.filter(criteria)
            .stream()
            .map(ToggleMapper.toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<UUID> createToggleRef(final UUID toggleId, final Collection<ToggleReference> value) {

        return toggleRefRepo.create(
            toggleId,
            value
                .stream()
                .map(ToggleReferenceMapper.toEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public void updateToggleRef(final UUID toggleId, final Collection<ToggleReference> value) {

        toggleRefRepo.update(
            toggleId,
            value
                .stream()
                .map(ToggleReferenceMapper.toEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteToggleRef(final UUID toggleId, final Collection<UUID> value) {

        toggleRefRepo.delete(toggleId, value);
    }

    @Override
    public ToggleReference findToggleRef(final UUID toggleId, final UUID id) {

        ToggleReferenceEntity toggleReferenceEntity = toggleRefRepo.find(toggleId, id);
        return ToggleReferenceMapper.toDto.apply(toggleReferenceEntity);
    }

    @Override
    public Collection<ToggleReference> filterToggleRef(final UUID toggleId) {

        return toggleRefRepo.filter(toggleId)
            .stream()
            .map(ToggleReferenceMapper.toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<SysToggle> findAllSysToggles(final String sysId, final String sysVersion) {

        Collection<SysToggleEntity> sysToggles = sysToggleRepo.filter(sysId, sysVersion);

        // a specific system item should override a global one
        Map<String, SysToggle> entries = sysToggles
            .stream()
            .filter(e -> e.getType().equals(ToggleType.GLOBAL))
            .collect(Collectors.toMap(SysToggleMapper.keyMapper, SysToggleMapper.valueMapper));
        entries.putAll(sysToggles
            .stream()
            .filter(e -> e.getType().equals(ToggleType.SYS))
            .collect(Collectors.toMap(SysToggleMapper.keyMapper, SysToggleMapper.valueMapper)));

        return entries.values();
    }
}
