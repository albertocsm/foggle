package com.github.albertocsm.foggle.impl.mapper;

import com.github.albertocsm.foggle.bll.dto.Toggle;
import com.github.albertocsm.foggle.impl.entity.ToggleEntity;

import java.util.function.Function;

public class ToggleMapper {

    public static Function<Toggle, ToggleEntity> toEntity = (dto) -> {
        if (dto != null) {
            return new ToggleEntity()
                .setActive(dto.isActive())
                .setDescription(dto.getDescription())
                .setGlobal(dto.isGlobal());
        } else return null;
    };


    public static Function<ToggleEntity, Toggle> toDto = (entity) -> {
        if (entity != null) {
            return new Toggle()
                .setResourceId(entity.getId())
                .setActive(entity.isActive())
                .setDescription(entity.getDescription())
                .setUpdatedAt(entity.getUpdatedAt())
                .setGlobal(entity.isGlobal());
        } else return null;
    };
}
