package com.github.albertocsm.foggle.impl.dal;

import com.github.albertocsm.foggle.bll.exception.NotFoundException;
import com.github.albertocsm.foggle.bll.exception.PersistenceException;
import com.github.albertocsm.foggle.impl.entity.ToggleEntity;
import com.github.albertocsm.foggle.impl.entity.ToggleReferenceEntity;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep6;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.albertocsm.foggle.h2.Tables.TOGGLE_REFERENCE;

/**
 * Provides an API to manage <code>{@link ToggleEntity}</code> references associated to
 * external systems or services
 */
@Repository
public class ToggleReferenceRepository {

    @Autowired
    private DSLContext dsl;
    
    public Collection<UUID> create(final UUID toggleId, final Collection<ToggleReferenceEntity> valueCollection) {

        Set<UUID> result;
        try {

            // prepare insert
            InsertValuesStep6 statement = dsl
                .insertInto(TOGGLE_REFERENCE,
                    TOGGLE_REFERENCE.ID,
                    TOGGLE_REFERENCE.TOGGLE_ID,
                    TOGGLE_REFERENCE.SYS_ID,
                    TOGGLE_REFERENCE.SYS_VERSION,
                    TOGGLE_REFERENCE.ACTIVE,
                    TOGGLE_REFERENCE.UPDATED_AT);

            Set<UUID> idCollection = new HashSet<>();
            valueCollection.forEach(e -> {

                UUID id = UUID.randomUUID();
                statement.values(
                    id.toString(),
                    toggleId,
                    e.getSysId(),
                    e.getSysVersion(),
                    e.isActive(),
                    System.currentTimeMillis());
                idCollection.add(id);
            });


            statement.execute();
            result = idCollection;

        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;
    }

    public void update(final UUID toggleId, final Collection<ToggleReferenceEntity> value) {


        try {

            Set<Query> queries = new HashSet<>();

            value.forEach(e ->
                queries.add(
                    dsl
                        .update(TOGGLE_REFERENCE)
                        .set(TOGGLE_REFERENCE.SYS_ID, e.getSysId())
                        .set(TOGGLE_REFERENCE.SYS_VERSION, e.getSysVersion())
                        .set(TOGGLE_REFERENCE.ACTIVE, e.isActive())
                        .set(TOGGLE_REFERENCE.UPDATED_AT, System.currentTimeMillis())
                        .where(
                            TOGGLE_REFERENCE.ID.equal(e.getId().toString()),
                            TOGGLE_REFERENCE.TOGGLE_ID.equal(toggleId.toString()),
                            TOGGLE_REFERENCE.DELETED_AT.isNull())));

            int[] execute = dsl
                .batch(queries)
                .execute();

            for (int i : execute) {
                if (i != 1) {
                    throw new NotFoundException();
                }
            }
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }
    }

    public void delete(final UUID toggleId, final Collection<UUID> value) {

        try {

            int execute = dsl
                .update(TOGGLE_REFERENCE)
                .set(TOGGLE_REFERENCE.DELETED_AT, System.currentTimeMillis())
                .where(
                    TOGGLE_REFERENCE.ID.in(value),
                    TOGGLE_REFERENCE.TOGGLE_ID.equal(toggleId.toString()),
                    TOGGLE_REFERENCE.DELETED_AT.isNull())
                .execute();

            if (execute != value.size()) {

                throw new NotFoundException();
            }
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }
    }

    public ToggleReferenceEntity find(final UUID toggleId, final UUID id) {

        ToggleReferenceEntity result;
        try {

            Record r = dsl
                .select()
                .from(TOGGLE_REFERENCE)
                .where(
                    TOGGLE_REFERENCE.TOGGLE_ID.equal(toggleId.toString()),
                    TOGGLE_REFERENCE.ID.equal(id.toString()),
                    TOGGLE_REFERENCE.DELETED_AT.isNull())
                .fetchOne();

            if (r != null) {
                result = r.into(ToggleReferenceEntity.class);
            } else {
                throw new NotFoundException();
            }
        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;
    }

    public Collection<ToggleReferenceEntity> filter(final UUID toggleId) {

        List<ToggleReferenceEntity> result;
        try {

            result = dsl
                .select()
                .from(TOGGLE_REFERENCE)
                .where(
                    TOGGLE_REFERENCE.TOGGLE_ID.equal(toggleId.toString()),
                    TOGGLE_REFERENCE.DELETED_AT.isNull())
                .fetch()
                .into(ToggleReferenceEntity.class);

        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;
    }
}
