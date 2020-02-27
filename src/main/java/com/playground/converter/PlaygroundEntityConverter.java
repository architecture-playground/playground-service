package com.playground.converter;

import com.playground.dto.PlaygroundEntityDTO;
import com.playground.model.PlaygroundEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PlaygroundEntityConverter {

    public PlaygroundEntityDTO toDto(PlaygroundEntity entitySource) {
        PlaygroundEntityDTO dtoTarget = new PlaygroundEntityDTO();
        BeanUtils.copyProperties(entitySource, dtoTarget);
        return dtoTarget;
    }

    public PlaygroundEntity toEntity(PlaygroundEntityDTO dtoSource) {
        PlaygroundEntity entityTarget = new PlaygroundEntity();
        BeanUtils.copyProperties(dtoSource, entityTarget);
        return entityTarget;
    }
}
