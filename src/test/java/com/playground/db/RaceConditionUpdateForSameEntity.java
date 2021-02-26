package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.PlaygroundEntity;
import com.playground.model.PlaygroundEntityStatus;
import com.playground.repository.PlaygroundEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@PlaygroundIntegrationTest
public class RaceConditionUpdateForSameEntity {

    @Autowired
    private PlaygroundEntityRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRaceConditionUpdate() {
        UUID originId = repository.save(new PlaygroundEntity()).getId();

        PlaygroundEntity first = repository.findById(originId).get();
        PlaygroundEntity second = repository.findById(originId).get();

        first.setStatus(PlaygroundEntityStatus.SUCCESS);
        repository.save(first);

        second.setComment("Check race update");
        repository.save(second);

        PlaygroundEntity afterUpdates = repository.findById(originId).get();
        assertThat(afterUpdates.getComment()).isEqualTo("Check race update");
        assertThat(afterUpdates.getStatus()).isNull(); // it's overwritten :)
    }
}
