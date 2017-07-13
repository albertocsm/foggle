package com.github.albertocsm.foggle.impl.dal;

import com.github.albertocsm.foggle.bll.exception.NotFoundException;
import com.github.albertocsm.foggle.impl.entity.ToggleEntity;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToggleRepositoryTest {

    @Autowired
    private ToggleRepository toggleRepository;

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void destroy() {
    }

    @Test
    public void test_create_toggle() {

        UUID id = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        Assert.assertNotNull(id);
    }

    @Test
    public void test_find_all_toggles() {

        int initialToggleCount = toggleRepository.all().size();

        toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(false)
            .setGlobal(true));

        Assert.assertEquals(initialToggleCount + 2, toggleRepository.all().size());
    }

    @Test
    public void test_find_a_toggle() {

        UUID id = toggleRepository.create(new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true));

        ToggleEntity item = toggleRepository.find(id);
        Assert.assertNotNull(item);


        Assert.assertNotNull(item.getId());
        Assert.assertTrue(item.isActive());
        Assert.assertTrue(item.isGlobal());
        Assert.assertNotNull(item.getUpdatedAt());
        Assert.assertTrue(item.getUpdatedAt() > 0);
    }

    @Test
    public void test_update_toggle() {

        // will need a new entry
        ToggleEntity newToggleEntity = new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true);
        UUID newToggleId = toggleRepository.create(newToggleEntity);
        newToggleEntity = toggleRepository.find(newToggleId);
        Assert.assertEquals(true, newToggleEntity.isActive());
        Assert.assertEquals(true, newToggleEntity.isGlobal());

        // update its state and verify
        toggleRepository.update(
            newToggleId,
            new ToggleEntity()
                .setDescription(newToggleEntity.getDescription() + "updated")
                .setGlobal(false)
                .setActive(false));

        ToggleEntity updatedToggleEntity = toggleRepository.find(newToggleId);

        Assert.assertEquals(newToggleEntity.getId(), updatedToggleEntity.getId());
        Assert.assertNotNull(updatedToggleEntity.getUpdatedAt());
        Assert.assertNotEquals(newToggleEntity.getUpdatedAt(), updatedToggleEntity.getUpdatedAt());
        Assert.assertTrue(updatedToggleEntity.getDescription().contains("updated"));
        Assert.assertEquals(false, updatedToggleEntity.isActive());
        Assert.assertEquals(false, updatedToggleEntity.isGlobal());
    }

    @Test(expected = NotFoundException.class)
    public void test_delete_toggle() {

        // will need a new entry
        ToggleEntity newToggleEntity = new ToggleEntity()
            .setDescription(String.format("t_%s", UUID.randomUUID().toString()))
            .setActive(true)
            .setGlobal(true);
        UUID newToggleId = toggleRepository.create(newToggleEntity);

        // delete it and verify
        toggleRepository.delete(newToggleId);
        toggleRepository.find(newToggleId);
    }
}
