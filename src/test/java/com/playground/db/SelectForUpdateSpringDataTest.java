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
    void testSelectForUpdateViaLockPessimisticWrite() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(4);
        AtomicInteger waitTimeCoefficient = new AtomicInteger(1);
        for (int i = 0; i < 3; i++) {
            service.submit(() -> {
                try {
                    log("Started thread [{0}]", threadName());
                    txWrapper.runInTransaction(() -> {
                        log("   Started transaction in thread [{0}] at {1}", threadName(), now());

                        log("Trying select entity at {0} in thread {1}", now(), threadName());
                        PlaygroundEntity entity = entityRepository.findForPessimisticWriteById(entityId);
                        log("Entity [{0}] selected at {1} in thread {2}", entity.getId(), now(), threadName());

                        if (entity.getStatus() != null) {
                            log("Entity [{0}] already has status, stop thread {1} at {2}", entity.getId(), threadName(), now());
                            return;
                        } else {
                            entity.setStatus(PlaygroundEntityStatus.SUCCESS);
                            entity.setComment("Updated from Select for Update");
                            log("Update entity state in thread {0} at {1}: [{2}]", threadName(), now(), entity.getId());
                        }

                        int waitTimeInMs = 4000 * waitTimeCoefficient.incrementAndGet();
                        log("Thread {0} sleep {1} millis from {2}", threadName(), waitTimeInMs, now());
                        waitMillis(waitTimeInMs);

                        log("Thread {0} await at {1}", threadName(), now());
                    });
                } finally {
                    latch.countDown();
                }
            });
        }

        service.submit(() -> {
            try {
                waitMillis(1000);

                log("Started thread [{0}]", threadName());
                log("Trying simple select entity at {0} in thread {1}", now(), threadName());
                PlaygroundEntity entity = entityRepository.findById(entityId).orElseThrow();
                log("Entity [{0}] simply selected at {1} in thread {2}", entity.getId(), now(), threadName());
                log("Simple selected entity state [{0}]", entity);

                if (entity.getStatus() != null) {
                    log("Simply selected entity [{0}] already has status, stop thread {1} at {2}", entity.getId(), threadName(), now());
                } else {
                    entity.setComment("Updated from simple select");
                    log("Start update simply selected entity [{0}] status in thread {1} at {2}", entity.getId(), threadName(), now());
                    entityRepository.saveAndFlush(entity);
                    log("Finish update simply selected entity [{0}] status in thread {1} at {2}", entity.getId(), threadName(), now());
                }
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        log("       ||||| Final entity state {0}", entityRepository.findById(entityId));
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