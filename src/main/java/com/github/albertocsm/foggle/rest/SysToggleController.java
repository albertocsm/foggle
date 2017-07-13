package com.github.albertocsm.foggle.rest;

import com.github.albertocsm.foggle.bll.dto.SysToggle;
import com.github.albertocsm.foggle.bll.SysToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/sys")
public class SysToggleController {

    @Autowired
    private SysToggleService sysToggleSrv;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
        @RequestParam(value = "s") final String sysId,
        @RequestParam(value = "mapper") final String sysVersion) {

        Collection<SysToggle> allSysToggles = sysToggleSrv.findAllSysToggles(sysId, sysVersion);

        return ResponseEntity.ok(allSysToggles);
    }
}
