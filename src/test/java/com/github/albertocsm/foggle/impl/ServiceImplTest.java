package com.github.albertocsm.foggle.impl;

import com.github.albertocsm.foggle.impl.dal.SysToggleRepository;
import com.github.albertocsm.foggle.impl.dal.ToggleReferenceRepository;
import com.github.albertocsm.foggle.impl.dal.ToggleRepository;
import com.github.albertocsm.foggle.bll.dto.SysToggle;
import com.github.albertocsm.foggle.bll.dto.ToggleType;
import com.github.albertocsm.foggle.impl.entity.SysToggleEntity;
import com.github.albertocsm.foggle.impl.ServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceImplTest {

    @MockBean
    private ToggleRepository toggleRepo;
    @MockBean
    private ToggleReferenceRepository toggleRefRepo;
    @MockBean
    private SysToggleRepository sysToggleRepo;

    @Autowired
    private ServiceImpl toggleService;

    @Test
    public void test_specific_toggle_overrides_global_for_sys() {

        toggleService = new ServiceImpl(toggleRepo, toggleRefRepo, sysToggleRepo);

        String toggleName = "t1";
        String sysId = "sys1";
        String sysVersion = "v1";


        // mock service internals
        Collection<SysToggleEntity> allToggles = new ArrayList<SysToggleEntity>() {{
            add(new SysToggleEntity()
                .setDescription(toggleName)
                .setActive(false)
                .setType(ToggleType.GLOBAL));
            add(new SysToggleEntity()
                .setDescription(toggleName)
                .setActive(true)
                .setType(ToggleType.SYS));
        }};
        Mockito.when(sysToggleRepo.filter(any(), any())).thenReturn(allToggles);

        // query and verify the sys specific toggle overrides the global one
        Collection<SysToggle> sysToggleCollection = toggleService.findAllSysToggles(sysId, sysVersion);
        Assert.assertEquals(1, sysToggleCollection.size());
        Optional<SysToggle> first = sysToggleCollection
            .stream()
            .findFirst();
        Assert.assertTrue(first.isPresent());
        Assert.assertEquals(true, first.get().isActive());
        Assert.assertEquals(toggleName, first.get().getDescription());
    }

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void destroy() {
    }
}
