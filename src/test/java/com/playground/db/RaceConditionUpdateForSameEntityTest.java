package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.DynamicUpdateItem;
import com.playground.model.PlaygroundEntity;
import com.playground.model.PlaygroundEntityStatus;
import com.playground.repository.PlaygroundEntityRepository;
import com.playground.repository.PlaygroundItemRepository;
import com.playground.util.TransactionalWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@PlaygroundIntegrationTest
public class RaceConditionUpdateForSameEntityTest {

    public static final UUID OBJECT_ID = UUID.randomUUID();
    @Autowired
    private PlaygroundEntityRepository entityRepository;

    @Autowired
    private PlaygroundItemRepository itemRepository;

    @Autowired
    private TransactionalWrapper txWrapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRaceConditionWithoutDynamicUpdate() {
        UUID originId = entityRepository.save(new PlaygroundEntity()).getId();

        PlaygroundEntity first = entityRepository.findById(originId).get();
        PlaygroundEntity second = entityRepository.findById(originId).get();

        first.setStatus(PlaygroundEntityStatus.SUCCESS);
        entityRepository.save(first);

        second.setComment("Check race update");
        entityRepository.save(second);

        PlaygroundEntity afterUpdates = entityRepository.findById(originId).get();
        assertThat(afterUpdates.getComment()).isEqualTo("Check race update");
        assertThat(afterUpdates.getStatus()).isNull(); // it's overwritten :)
    }

    @Test
    void testRaceConditionWithDynamicUpdateWhithoutTransaction() {
        UUID originId = itemRepository.save(new DynamicUpdateItem()).getId();

        DynamicUpdateItem first = itemRepository.findById(originId).get();
        DynamicUpdateItem second = itemRepository.findById(originId).get();

        first.setObjectId(OBJECT_ID);
        itemRepository.save(first);

        // Due to we don't inside transaction, then "current" entity state was loaded from DB not from "second"
        // see org.hibernate.loader.Loader#hydrateEntityState
        second.setComment("Check race update");
        itemRepository.save(second);

        DynamicUpdateItem afterUpdates = itemRepository.findById(originId).get();
        assertThat(afterUpdates.getComment()).isEqualTo("Check race update");

        // DynamicUpdate is only about minimizing executed query if you has entity with 60 fields and usually update only 1 or 2 fields

        // org.hibernate.type.AbstractStandardBasicType.isDirty(java.lang.Object, java.lang.Object)
        // protected final boolean isDirty(Object old, Object current) {
        // 	 return !isSame( old, current );
        // }
        assertThat(afterUpdates.getObjectId()).isNull(); // it's still overwritten :)
    }

    @Test
    void testRaceConditionWithDynamicUpdateInsideTransaction() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(2);

        UUID originId = itemRepository.save(new DynamicUpdateItem()).getId();

        executor.submit(() -> {
            log("Started thread [{0}]", threadName());
            txWrapper.runInTransaction(() -> {
                DynamicUpdateItem first = itemRepository.findById(originId).get();
                waitMillis(200);

                first.setObjectId(OBJECT_ID);
                itemRepository.save(first);
            });

            latch.countDown();
            log("Finished thread [{0}]", threadName());
        });

        executor.submit(() -> {
            log("Started thread [{0}]", threadName());
            txWrapper.runInTransaction(() -> {
                DynamicUpdateItem second = itemRepository.findById(originId).get();
                waitMillis(400);

                second.setComment("Check race update");
                itemRepository.save(second);
            });

            latch.countDown();
            log("Finished thread [{0}]", threadName());
        });

        latch.await();

        DynamicUpdateItem afterUpdates = itemRepository.findById(originId).get();
        assertThat(afterUpdates.getComment()).isEqualTo("Check race update");
        assertThat(afterUpdates.getObjectId()).isEqualTo(OBJECT_ID); // it's NOT overwritten :)
    }

    /* INTERNAL */

    private static void log(String pattern, Object... arguments) {
        System.out.println(format(pattern, arguments));
    }

    private String threadName() {
        return Thread.currentThread().getName();
    }

    private void waitMillis(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
