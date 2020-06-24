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
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PlaygroundEntity findLockById(UUID playgroundEntityId);
}
