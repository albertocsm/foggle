package com.github.albertocsm.foggle.impl.dal;

import com.github.albertocsm.foggle.bll.exception.NotFoundException;
import com.github.albertocsm.foggle.bll.exception.PersistenceException;
import com.github.albertocsm.foggle.bll.dto.ToggleCriteria;
import com.github.albertocsm.foggle.impl.entity.ToggleEntity;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.albertocsm.foggle.h2.Tables.TOGGLE;

/**
 * Provides a management API to the resource
 */
@Repository
public class ToggleRepository {

    @Autowired
    private DSLContext dsl;

    public UUID create(final ToggleEntity value) {

        UUID result;
        try {

            UUID id = UUID.randomUUID();
            dsl
                .insertInto(TOGGLE)
                .set(TOGGLE.ID, id.toString())
                .set(TOGGLE.DESCRIPTION, value.getDescription())
                .set(TOGGLE.ACTIVE, value.isActive())
                .set(TOGGLE.GLOBAL, value.isGlobal())
                .set(TOGGLE.UPDATED_AT, System.currentTimeMillis())
                .execute();

            result = id;
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;
    }

    public void update(final UUID id, final ToggleEntity value) {

        try {

            int execute = dsl
                .update(TOGGLE)
                .set(TOGGLE.DESCRIPTION, value.getDescription())
                .set(TOGGLE.ACTIVE, value.isActive())
                .set(TOGGLE.GLOBAL, value.isGlobal())
                .set(TOGGLE.UPDATED_AT, System.currentTimeMillis())
                .where(
                    TOGGLE.ID.equal(id.toString()),
                    TOGGLE.DELETED_AT.isNull())
                .execute();

            if (execute != 1) {

                throw new NotFoundException();
            }
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }
    }

    public void delete(final UUID id) {

        try {

            int execute = dsl
                .update(TOGGLE)
                .set(TOGGLE.DELETED_AT, System.currentTimeMillis())
                .where(
                    TOGGLE.ID.equal(id.toString()),
                    TOGGLE.DELETED_AT.isNull())
                .execute();

            if (execute != 1) {

                throw new NotFoundException();
            }
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }
    }

    public ToggleEntity find(final UUID id) {

        ToggleEntity result;
        try {

            Record r = dsl
                .select()
                .from(TOGGLE)
                .where(
                    TOGGLE.ID.equal(id.toString()),
                    TOGGLE.DELETED_AT.isNull())
                .fetchOne();

            if (r != null) {
                result = r.into(ToggleEntity.class);
            } else {
                throw new NotFoundException();
            }
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;
    }

    public Collection<ToggleEntity> all() {

        return filter(new ToggleCriteria());
    }

    public Collection<ToggleEntity> filter(final ToggleCriteria criteria) {

        Set<Condition> conditions = new HashSet<>();
        conditions.add(TOGGLE.DELETED_AT.isNull());

        if (criteria.getActive() != null) {

            conditions.add(TOGGLE.ACTIVE.equal(criteria.getActive()));
        }
        if (criteria.getGlobal() != null) {

            conditions.add(TOGGLE.GLOBAL.equal(criteria.getGlobal()));
        }

        List<ToggleEntity> result = null;
        try {
            result = dsl
                .select()
                .from(TOGGLE)
                .where(conditions.toArray(new Condition[conditions.size()]))
                .fetch()
                .into(ToggleEntity.class);

        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;
    }
}
