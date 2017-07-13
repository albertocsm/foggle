CREATE TABLE TOGGLE (
	id char(55) NOT NULL,
	description varchar(255) not null,
	active boolean  not null,
	"global" boolean  not null,
	updated_at bigint not null,
	deleted_at bigint
);

CREATE TABLE TOGGLE_REFERENCE (
    id char(55) NOT NULL,
    toggle_id char(55) NOT NULL,
    sys_id varchar(255) not null,
    sys_version varchar(255) not null,
    active boolean  not null,
    updated_at bigint not null,
    deleted_at bigint
);

CREATE VIEW ALL_SYS_TOGGLES as
(
    select
        'GLOBAL' as record_type,
        T.id as toggle_id,
        T.description,
        T.active,
        null as sys_id,
        null as sys_version
    from
        TOGGLE T
    where
        T.deleted_at is null
        and T."global" = true
    UNION ALL
    select
        'SYS' as record_type,
        T.id as toggle_id,
        T.description,
        TR.active,
        TR.sys_id,
        TR.sys_version
    from
        TOGGLE T

    inner join TOGGLE_REFERENCE TR on
        TR.toggle_id = T.id
        AND TR.deleted_at is null
    where
        T.deleted_at is null
);