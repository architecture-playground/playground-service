package com.playground.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@Data
@Entity
@Table(schema = "playground", name = "specification_item")
public class SpecificationItem {

    /**
     * see {@link org.hibernate.type.PostgresUUIDType}
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @Type(type = "pgsql-enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlaygroundEntityStatus status;

    @Type(type = "PersistentDateTime")
    @Getter
    private DateTime jodaDateTime;

    public SpecificationItem(PlaygroundEntityStatus status, DateTime jodaDateTime) {
        this.status = status;
        this.jodaDateTime = jodaDateTime;
    }
}
