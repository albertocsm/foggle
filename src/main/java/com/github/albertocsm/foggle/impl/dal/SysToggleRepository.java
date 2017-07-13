package com.github.albertocsm.foggle.impl.dal;

import com.github.albertocsm.foggle.bll.exception.PersistenceException;
import com.github.albertocsm.foggle.impl.entity.SysToggleEntity;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static com.github.albertocsm.foggle.h2.Tables.ALL_SYS_TOGGLES;

/**
 * Provides an API that enables an external system or service to query and retrieve it's current toggles
 */
@Repository
public class SysToggleRepository {

    @Autowired
    private DSLContext dsl;

    public Collection<SysToggleEntity> filter(final String sysId, final String sysVersion) {

        Collection<SysToggleEntity> result = null;
        try {

            result = dsl
                .select()
                .from(ALL_SYS_TOGGLES)
                .where(ALL_SYS_TOGGLES.SYS_ID.equal(sysId).and(ALL_SYS_TOGGLES.SYS_VERSION.equal(sysVersion)))
                .or(ALL_SYS_TOGGLES.SYS_ID.isNull().and(ALL_SYS_TOGGLES.SYS_VERSION.isNull()))
                .fetch()
                .into(SysToggleEntity.class);

        } catch (DataAccessException e) {

            throw new PersistenceException(String.format("Unexpected error occurred - %s", e));
        }

        return result;

    }
}
