package com.github.albertocsm.foggle.impl.mapper;

import com.github.albertocsm.foggle.bll.dto.SysToggle;
import com.github.albertocsm.foggle.impl.entity.SysToggleEntity;

import java.util.function.Function;

public class SysToggleMapper {

    public static Function<SysToggleEntity, String> keyMapper = t ->
        t.getDescription();

    public static Function<SysToggleEntity, SysToggle> valueMapper = t ->
        new SysToggle()
            .setDescription(t.getDescription())
            .setActive(t.isActive());
}
