CREATE TABLE playground.versioned_entity
(
    id      uuid NOT NULL PRIMARY KEY,
    comment text,
    version bigint
);
