package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.VersionedEntity;
import com.playground.repository.VersionedEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PlaygroundIntegrationTest
public class VersionLockTest {

    @Autowired
    private VersionedEntityRepository repository;

    @Test
    void testOptimisticLockWithVersionAnnotation() {
        // given
        var id = repository.save(new VersionedEntity("Test Optimistic Lock from @Version annotation")).getId();
        var fetched1 = repository.findById(id).orElseThrow();
        var fetched2 = repository.findById(id).orElseThrow();

        // when
        fetched1.setComment("changed1");
        repository.save(fetched1);

        fetched2.setComment("changed2");
        Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () -> repository.save(fetched2));
    }
}
