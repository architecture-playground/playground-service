package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.PlaygroundEntity;
import com.playground.repository.PlaygroundEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@PlaygroundIntegrationTest
public class BatchDeleteTest {

    @Autowired
    private PlaygroundEntityRepository repository;

    @Transactional
    @Test
    void testSpringDataDeleteIdIn() {
        List<PlaygroundEntity> newEntities = createRandomEntities(5);
        repository.saveAll(newEntities);

        List<UUID> createdIds = newEntities.stream().map(PlaygroundEntity::getId).collect(Collectors.toList());
        repository.deleteAllByIdIn(createdIds);
    }

    private static List<PlaygroundEntity> createRandomEntities(int count) {
        List<PlaygroundEntity> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            PlaygroundEntity toAdd = new PlaygroundEntity();
            toAdd.setObjectId(UUID.randomUUID());
            toAdd.setComment("This is comment!");
            result.add(toAdd);
        }

        return result;
    }
}
