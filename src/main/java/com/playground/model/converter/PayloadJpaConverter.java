package com.playground.model.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.dto.Payload;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class PayloadJpaConverter implements AttributeConverter<Payload, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Payload attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public Payload convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, Payload.class);
    }
}
