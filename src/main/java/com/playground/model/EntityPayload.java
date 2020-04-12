package com.playground.model;

import com.playground.dto.Payload;
import com.playground.model.converter.PayloadJpaConverter;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(schema = "playground", name = "payload_entity")
public class EntityPayload {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    private UUID id;

    @Convert(converter = PayloadJpaConverter.class)
    private Payload payload;
}
