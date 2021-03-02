package com.playground.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@DynamicUpdate
@Slf4j
@Data
@Entity
@Table(schema = "playground", name = "dynamic_update_item")
public class DynamicUpdateItem {

    /**
     * see {@link org.hibernate.type.PostgresUUIDType}
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @Type(type = "pg-uuid")
    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "comment")
    private String comment;
}
