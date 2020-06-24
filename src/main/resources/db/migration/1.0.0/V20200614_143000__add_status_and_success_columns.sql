CREATE TYPE playground.playground_entity_status as ENUM (
    'INITIAL',
    'PENDING',
    'SUCCESS',
    'FAILED',
    'EXPIRED',
    'CANCELED'
    );

ALTER TABLE playground.playground_entity
    ADD COLUMN status  playground.playground_entity_status,
    ADD COLUMN succeed BOOLEAN
