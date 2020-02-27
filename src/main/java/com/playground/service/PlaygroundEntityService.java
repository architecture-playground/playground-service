package com.playground.service;

import com.playground.converter.PlaygroundEntityConverter;
import com.playground.dto.CreatedPlaygroundEntityDTO;
import com.playground.dto.PlaygroundEntityDTO;
import com.playground.model.PlaygroundEntity;
import com.playground.repository.PlaygroundEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atteo.evo.inflector.English;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaygroundEntityService {

    private final PlaygroundEntityRepository playgroundEntityRepository;
    private final PlaygroundEntityConverter playgroundEntityConverter;

    public List<PlaygroundEntityDTO> getAllEntities() {
        List<PlaygroundEntity> found = playgroundEntityRepository.findAll();
        log.info("Found {} {}", found.size(), English.plural("entity"));
        return found.stream()
                .map(playgroundEntityConverter::toDto)
                .collect(Collectors.toList());
    }

    public CreatedPlaygroundEntityDTO createOne(PlaygroundEntityDTO dto) {
        PlaygroundEntity created = playgroundEntityRepository.save(playgroundEntityConverter.toEntity(dto));
        log.info("Entity {} created successfully", created.getId());
        return new CreatedPlaygroundEntityDTO(created.getId());
    }
}
