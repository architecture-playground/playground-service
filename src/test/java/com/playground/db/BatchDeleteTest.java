package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.PlaygroundEntity;
import com.playground.repository.PlaygroundEntityRepository;
import com.playground.util.TransactionalWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@PlaygroundIntegrationTest
public class BatchDeleteTest {

    @Autowired
    private PlaygroundEntityRepository repository;

    @Autowired
    private TransactionalWrapper txWrapper;

    @Test
    void testSpringDataDeleteIdIn() {
        List<PlaygroundEntity> newEntities = repository.saveAll(createRandomEntities(5));
        repository.flush(); // just to be sure that all data are persisted

        List<UUID> createdIds = newEntities.stream().map(PlaygroundEntity::getId).collect(Collectors.toList());
        txWrapper.runInTransaction(() -> {
            // must be called inside transaction to prevent the following error:
            //  TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
            repository.deleteAllByIdIn(createdIds);
        });

        // as we can se this deletion generates the following SQL
        // 2020-07-28 00:28:56.115 TRACE 10733 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [5] as [OTHER] - [27640512-b87f-441d-8ada-883b80ea53e4]
        // 2020-07-28 00:28:56.146 DEBUG 10733 --- [    Test worker] org.hibernate.SQL                        : delete from playground.playground_entity where id=?
        // 2020-07-28 00:28:56.147 TRACE 10733 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [OTHER] - [7662d3ef-e21c-47c8-9ab4-2b5cc62c29a5]
        // 2020-07-28 00:28:56.149 DEBUG 10733 --- [    Test worker] org.hibernate.SQL                        : delete from playground.playground_entity where id=?
        // 2020-07-28 00:28:56.149 TRACE 10733 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [OTHER] - [9f691983-2405-4071-8cae-c7d8bf0bdcc3]
        // 2020-07-28 00:28:56.149 DEBUG 10733 --- [    Test worker] org.hibernate.SQL                        : delete from playground.playground_entity where id=?
        // 2020-07-28 00:28:56.150 TRACE 10733 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [OTHER] - [1f63162f-0aef-4bef-9b07-e9893eefda71]
        // 2020-07-28 00:28:56.150 DEBUG 10733 --- [    Test worker] org.hibernate.SQL                        : delete from playground.playground_entity where id=?
        // 2020-07-28 00:28:56.150 TRACE 10733 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [OTHER] - [b24a729c-dbbe-49ea-a1d0-5433b88113cd]
        // 2020-07-28 00:28:56.151 DEBUG 10733 --- [    Test worker] org.hibernate.SQL                        : delete from playground.playground_entity where id=?
        // 2020-07-28 00:28:56.151 TRACE 10733 --- [    Test worker] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [OTHER] - [27640512-b87f-441d-8ada-883b80ea53e4]
        assertThat(repository.findAllById(createdIds)).isEmpty();
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
