CREATE TABLE playground.dynamic_update_item
(
    id        uuid NOT NULL PRIMARY KEY,
    object_id uuid,
    comment   text
);