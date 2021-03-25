package com.playground.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@Data
@Entity
@Table(schema = "playground", name = "versioned_entity")
public class VersionedEntity {

    /**
     * see {@link org.hibernate.type.PostgresUUIDType}
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    private String comment;

    @Version
    private long version;

    public VersionedEntity(String comment) {
        this.comment = comment;
    }
}
