package com.github.albertocsm.foggle.impl.dal;

import com.github.albertocsm.foggle.bll.dto.ToggleCriteria;
import com.github.albertocsm.foggle.impl.entity.SysToggleEntity;
import com.github.albertocsm.foggle.impl.entity.ToggleEntity;
import com.github.albertocsm.foggle.impl.entity.ToggleReferenceEntity;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysToggleRepositoryTest {

    @Autowired
    private ToggleRepository toggleRepository;
    @Autowired
    private ToggleReferenceRepository toggleReferenceRepository;
    @Autowired
    private SysToggleRepository sysToggleRepository;

    @Test
    public void test_retrieve_specific_toggle_for_sys() {

        String sysId = String.format("sys_%s", UUID.randomUUID().toString());
        String sysVersion = "v1";

        // need to know how many globals exist beforehand
        Integer globalCount = toggleRepository.filter(new ToggleCriteria().setGlobal(true)).size();

        // need real toggles persisted
        String newToggleOneName = String.format("t_%s", UUID.randomUUID().toString());
        UUID newToggleOneId = toggleRepository.create(new ToggleEntity()
            .setDescription(newToggleOneName)
            .setActive(true)
            .setGlobal(false));

        // will need a sys referencing the Toggle
        Set<ToggleReferenceEntity> toggleReferenceEntities = new HashSet<>();
        toggleReferenceEntities.add(new ToggleReferenceEntity()
            .setActive(true)
            .setSysId(sysId)
            .setSysVersion(sysVersion)
            .setToggleId(newToggleOneId));
        toggleReferenceRepository.create(newToggleOneId, toggleReferenceEntities);

        Collection<SysToggleEntity> sysToggleCollection = sysToggleRepository.filter(sysId, sysVersion);
        Assert.assertEquals(globalCount + 1, sysToggleCollection.size());
        Optional<SysToggleEntity> first = sysToggleCollection
            .stream()
            .filter(e -> e.getDescription().equals(newToggleOneName))
            .findFirst();
        Assert.assertTrue(first.isPresent());
        Assert.assertEquals(true, first.get().isActive());
    }

    @Test
    public void test_retrieve_global_toggle_for_sys() {

        String sysId = String.format("sys_%s", UUID.randomUUID().toString());
        String sysVersion = "v1";

        // need to know how many globals exist beforehand
        Integer globalCount = toggleRepository.filter(new ToggleCriteria().setGlobal(true)).size();

        // need real toggles persisted
        String newToggleOneName = String.format("t_%s", UUID.randomUUID().toString());
        toggleRepository.create(new ToggleEntity()
            .setDescription(newToggleOneName)
            .setActive(true)
            .setGlobal(true));

        Collection<SysToggleEntity> sysToggleCollection = sysToggleRepository.filter(sysId, sysVersion);
        Assert.assertEquals(globalCount + 1, sysToggleCollection.size());
        Optional<SysToggleEntity> first = sysToggleCollection
            .stream()
            .filter(e -> e.getDescription().equals(newToggleOneName))
            .findFirst();
        Assert.assertTrue(first.isPresent());
        Assert.assertEquals(true, first.get().isActive());
    }

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void destroy() {
    }
}
