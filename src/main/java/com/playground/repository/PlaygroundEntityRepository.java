package com.playground.repository;

import com.playground.model.PlaygroundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface PlaygroundEntityRepository extends JpaRepository<PlaygroundEntity, UUID> {

    /**
     * Must be called from method marked with {@link org.springframework.transaction.annotation.Transactional}.
     * <p>
     * Read more here https://www.baeldung.com/spring-data-jpa-delete
     */
    long deleteAllByIdIn(Collection<UUID> ids);

    /**
     * Must be called from method marked with {@link org.springframework.transaction.annotation.Transactional}.
     * <p>
     * The PESSIMISTIC_READ acquires a shared (read) lock on the associated table row record, while the PESSIMISTIC_WRITE
     * acquires an exclusive (write) lock.
     * The shared lock blocks any other concurrent exclusive lock requests,
     * but it allows other shared lock requests to proceed.
     * <p>
     * The exclusive lock blocks both shared and exclusive lock requests.
     * What's worth mentioning is that, for Hibernate, if the database does not support shared locks (e.g. Oracle),
     * then a shared lock request (PESSIMISTIC_READ) will simply acquire an exclusive lock request (PESSIMISTIC_WRITE).
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PlaygroundEntity findForPessimisticWriteById(UUID playgroundEntityId);

    /**
     * Must be called from method marked with {@link org.springframework.transaction.annotation.Transactional}.
     * <p>
     * The PESSIMISTIC_READ acquires a shared (read) lock on the associated table row record, while the PESSIMISTIC_WRITE
     * acquires an exclusive (write) lock.
     *
     * The shared lock blocks any other concurrent exclusive lock requests,
     * but it allows other shared lock requests to proceed.
     * <p>
     * The exclusive lock blocks both shared and exclusive lock requests.
     * What's worth mentioning is that, for Hibernate, if the database does not support shared locks (e.g. Oracle),
     * then a shared lock request (PESSIMISTIC_READ) will simply acquire an exclusive lock request (PESSIMISTIC_WRITE).
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    PlaygroundEntity findForPessimisticReadById(UUID playgroundEntityId);
}
