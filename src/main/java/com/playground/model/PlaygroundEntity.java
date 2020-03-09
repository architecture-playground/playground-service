package com.playground.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Slf4j
@Data
@Entity
@Table(schema = "playground", name = "playground_entity")
public class PlaygroundEntity {

    /**
     * see {@link org.hibernate.type.PostgresUUIDType}
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    /**
     * see org.hibernate.type.UUIDBinaryType
     */
    @Type(type = "pg-uuid-binary")
    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "comment")
    private String comment;


    /* JPA entity lifecycle events */

    /**
     * What are the possible use cases for @PrePersist and @PreUpdate
     * The annotations are used to mark specific methods as callbacks for specific stages in the entity instance’s lifecycle.
     * Like any callback, the following use-cases are possible.
     * <p>
     * Perform custom validation checks before an entity is persisted in the database.
     * Set default values for empty or null entity members.
     * Perform per-update operations or calculating transient values.
     */
    @PrePersist
    public void onPrePersist() {
        log.debug("JPA lifecycle PrePersist event.");
    }

    @PreUpdate
    public void onPreUpdate() {
        log.debug("JPA lifecycle PreUpdate event.");
    }
}
