package com.playground.db;

import com.playground.PlaygroundIntegrationTest;
import com.playground.model.PlaygroundEntity;
import com.playground.model.PlaygroundEntityStatus;
import com.playground.repository.PlaygroundEntityRepository;
import com.playground.service.PlaygroundEntityService;
import com.playground.util.TransactionalWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.text.MessageFormat.format;
import static java.time.Instant.now;

@PlaygroundIntegrationTest
class SelectForUpdateSpringDataTest {

    @Autowired
    private PlaygroundEntityService entityService;

    @Autowired
    private TransactionalWrapper txWrapper;

    @Autowired
    private PlaygroundEntityRepository entityRepository;

    private UUID entityId;

    @BeforeEach
    void setUp() {
        List<PlaygroundEntity> entities = entityService.createRandom(1);
        entityId = entities.get(0).getId();
    }

    @Test
    void testSelectForUpdateWithLockPessimisticWrite() throws Exception {
        int numberOfThreads = 3;
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger waitTimeCoefficient = new AtomicInteger(1);
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                log("Started thread [{0}]", threadName());
                txWrapper.runInTransaction(() -> {
                    log("   Started transaction in thread [{0}] at {1}", threadName(), now());

                    log("Trying select entity at {0}", now());
                    PlaygroundEntity entity = entityRepository.findLockById(entityId);
                    log("Entity [{0}] selected at {1} in thread {2}", entity.getId(), now(), threadName());

                    if (entity.getStatus() != null) {
                        log("Entity [{0}] already has status, stop thread {1} at {2}", entity.getId(), threadName(), now());
                        return;
                    }
                    else {
                        log("Update entity [{0}] status in thread {1} at {2}", entity.getId(), threadName(), now());
                        entity.setStatus(PlaygroundEntityStatus.SUCCESS);
                    }

                    int waitTimeInMs = 2000 * waitTimeCoefficient.incrementAndGet();
                    log("Thread {0} sleep {1} millis from {2}", threadName(), waitTimeInMs, now());
                    waitMillis(waitTimeInMs);

                    log("Thread {0} await at {1}", threadName(), now());
                });
                latch.countDown();
            });
        }
        latch.await();
    }

    private void waitMillis(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private String threadName() {
        return Thread.currentThread().getName();
    }

    private static void log(String pattern, Object... arguments) {
        System.out.println(format(pattern, arguments));
    }
}