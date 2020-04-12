CREATE TABLE playground.playground_entity
(
    id        uuid NOT NULL PRIMARY KEY,
    object_id bytea,
    comment   text
);