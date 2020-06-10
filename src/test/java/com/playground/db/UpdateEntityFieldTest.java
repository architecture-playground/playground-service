package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.PlaygroundEntity;
import com.playground.repository.PlaygroundEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@PlaygroundIntegrationTest
public class UpdateEntityFieldTest {

    @Autowired
    private PlaygroundEntityRepository repository;

    @Test
    void setFieldToNull() {
        PlaygroundEntity toSave = new PlaygroundEntity();
        toSave.setObjectId(UUID.randomUUID());
        toSave.setComment("This is comment!");

        UUID savedId = repository.save(toSave).getId();
        repository.findById(savedId).ifPresent(
                it -> {
                    it.setComment(null);
                    repository.save(it);
                });

        assertThat(repository.findById(savedId))
                .hasValueSatisfying(it -> assertThat(it.getComment()).isNull());
    }
}
