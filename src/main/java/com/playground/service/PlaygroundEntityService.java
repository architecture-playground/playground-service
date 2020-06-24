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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    @Transactional
    public long removeAllEntities() {
        List<UUID> found = playgroundEntityRepository.findAll().stream()
                .map(PlaygroundEntity::getId)
                .collect(Collectors.toList());

        long removedItemsCount = playgroundEntityRepository.deleteAllByIdIn(found);
        log.info("Successfully removed {} entities", removedItemsCount);
        return removedItemsCount;
    }

    public CreatedPlaygroundEntityDTO createOne(PlaygroundEntityDTO dto) {
        PlaygroundEntity created = playgroundEntityRepository.save(playgroundEntityConverter.toEntity(dto));
        log.info("Entity {} created successfully", created.getId());
        return new CreatedPlaygroundEntityDTO(created.getId());
    }

    public List<PlaygroundEntity> createRandom(Integer quantity) {
        List<PlaygroundEntity> toSave = new ArrayList<>();
        for (int counter = 0; counter < quantity; counter++) {
            PlaygroundEntity entity = new PlaygroundEntity();
            entity.setObjectId(UUID.randomUUID());
            entity.setComment("Auto-generated entity");
            toSave.add(entity);
        }
        return playgroundEntityRepository.saveAll(toSave);
    }
}
