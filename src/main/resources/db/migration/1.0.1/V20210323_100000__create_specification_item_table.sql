CREATE TABLE playground.specification_item
(
    id             uuid NOT NULL PRIMARY KEY,
    status         playground.playground_entity_status,
    joda_date_time timestamp WITHOUT TIME ZONE
);