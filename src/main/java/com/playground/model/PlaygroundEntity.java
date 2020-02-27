package com.playground.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

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
}
