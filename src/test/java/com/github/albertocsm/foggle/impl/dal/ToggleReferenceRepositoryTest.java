package com.github.albertocsm.foggle.impl.dal;

import com.github.albertocsm.foggle.bll.exception.NotFoundException;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToggleReferenceRepositoryTest {

    @Autowired
    private ToggleReferenceRepository toggleReferenceRepository;

    @Autowired
    private ToggleRepository toggleRepository;

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void destroy() {
    }

    @Test
    public void test_create_toggle_reference() {

        // need a real toggle persisted
        UUID newToggleId = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        Set<ToggleReferenceEntity> toggleReferenceEntities = new HashSet<>();
        toggleReferenceEntities.add(new ToggleReferenceEntity()
            .setActive(true)
            .setSysId(UUID.randomUUID().toString())
            .setSysVersion("V1"));

        toggleReferenceEntities.add(new ToggleReferenceEntity()
            .setActive(true)
            .setSysId(UUID.randomUUID().toString())
            .setSysVersion("V1"));

        Collection<UUID> newSysToggleIdCollection = toggleReferenceRepository.create(newToggleId, toggleReferenceEntities);

        Assert.assertNotNull(newSysToggleIdCollection);
        Assert.assertEquals(2, newSysToggleIdCollection.size());
    }

    @Test
    public void test_update_toggle_reference() {

        // need a real toggle persisted
        UUID newToggleId = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        // will need a new entry
        Set<ToggleReferenceEntity> toggleReferenceEntities = new HashSet<>();
        toggleReferenceEntities.add(new ToggleReferenceEntity()
            .setActive(true)
            .setSysId(UUID.randomUUID().toString())
            .setSysVersion("V1"));
        Collection<UUID> newSysToggleIdCollection = toggleReferenceRepository.create(newToggleId, toggleReferenceEntities);
        UUID newSysToggleId = newSysToggleIdCollection.stream().findFirst().get();
        ToggleReferenceEntity newToggleReferenceEntity = toggleReferenceRepository.find(newToggleId, newSysToggleId);

        // update its state and verify
        newToggleReferenceEntity.setActive(false);
        toggleReferenceRepository.update(
            newToggleId,
            Collections.singleton(newToggleReferenceEntity));
        ToggleReferenceEntity updatedToggleReferenceEntity = toggleReferenceRepository.find(newToggleId, newSysToggleId);
        Assert.assertFalse(updatedToggleReferenceEntity.isActive());
    }

    @Test(expected = NotFoundException.class)
    public void test_delete_toggle_reference() {

        // need a real toggle persisted
        UUID newToggleId = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        // will need a new entry
        Set<ToggleReferenceEntity> toggleReferenceEntities = new HashSet<>();
        toggleReferenceEntities.add(new ToggleReferenceEntity()
            .setActive(true)
            .setSysId(UUID.randomUUID().toString())
            .setSysVersion("V1"));
        Collection<UUID> newSysToggleIdCollection = toggleReferenceRepository.create(newToggleId, toggleReferenceEntities);
        UUID newSysToggleId = newSysToggleIdCollection.stream().findFirst().get();

        // delete it and verify
        toggleReferenceRepository.delete(newToggleId, Collections.singleton(newSysToggleId));
        toggleReferenceRepository.find(newToggleId, newSysToggleId);
    }

    @Test
    public void test_find_all_toggle_references_from_specific_sys() {

        // need real toggles persisted
        UUID newToggleOneId = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        UUID newToggleTwoId = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        // will need new entries
        toggleReferenceRepository.create(
            newToggleOneId,
            Collections.singleton(
                new ToggleReferenceEntity()
                    .setActive(true)
                    .setSysId(UUID.randomUUID().toString())
                    .setSysVersion("V1")));

        toggleReferenceRepository.create(
            newToggleTwoId,
            Collections.singleton(
                new ToggleReferenceEntity()
                    .setActive(true)
                    .setSysId(UUID.randomUUID().toString())
                    .setSysVersion("V1")));


        Collection<ToggleReferenceEntity> toggleReferenceEntityCollection = toggleReferenceRepository.filter(newToggleOneId);
        Assert.assertEquals(1, toggleReferenceEntityCollection.size());
    }
}
