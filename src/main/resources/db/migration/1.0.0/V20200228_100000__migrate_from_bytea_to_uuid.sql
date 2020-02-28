-- create temp column with uuid type for migrating
ALTER TABLE playground.playground_entity
    ADD COLUMN object_id_as_uuid uuid;

-- convert and migrate data from deprecated bytea column to the uuid column
UPDATE playground.playground_entity pe
SET pe.object_id_as_uuid = cast(encode(pe.object_id, 'hex') as uuid);

ALTER TABLE playground.playground_entity
    RENAME COLUMN object_id TO old_object_id;

ALTER TABLE playground.playground_entity
    RENAME COLUMN object_id_as_uuid TO object_id;

ALTER TABLE playground.playground_entity
    DROP COLUMN old_object_id;