package com.github.albertocsm.foggle.impl.mapper;

import com.github.albertocsm.foggle.bll.dto.ToggleReference;
import com.github.albertocsm.foggle.impl.entity.ToggleReferenceEntity;

import java.util.function.Function;

public class ToggleReferenceMapper {

    public static Function<ToggleReference, ToggleReferenceEntity> toEntity = (dto) -> {
        if (dto != null) {
            return new ToggleReferenceEntity()
                .setActive(dto.isActive())
                .setSysId(dto.getSysId())
                .setSysVersion(dto.getSysVersion())
                .setId(dto.getResourceId());
        } else return null;
    };
    
    public static Function<ToggleReferenceEntity, ToggleReference> toDto = (entity) -> {
        if (entity != null) {
            return new ToggleReference()
                .setResourceId(entity.getId())
                .setActive(entity.isActive())
                .setSysId(entity.getSysId())
                .setSysVersion(entity.getSysVersion())
                .setUpdatedAt(entity.getUpdatedAt())
                .setToggleId(entity.getToggleId());
        } else return null;
    };
}
