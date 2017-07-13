package com.github.albertocsm.foggle.bll;

import com.github.albertocsm.foggle.bll.dto.SysToggle;

import java.util.Collection;

public interface SysToggleService {

    Collection<SysToggle> findAllSysToggles(String sysId, String sysVersion);
}
