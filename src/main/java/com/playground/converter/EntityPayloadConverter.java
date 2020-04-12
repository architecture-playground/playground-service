package com.playground.converter;

import com.playground.dto.EntityPayloadDTO;
import com.playground.model.EntityPayload;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityPayloadConverter {

    public EntityPayloadDTO toDto(EntityPayload source) {
        EntityPayloadDTO result = new EntityPayloadDTO();
        BeanUtils.copyProperties(source, result);
        return result;
    }

    public List<EntityPayloadDTO> toDtos(List<EntityPayload> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
