package com.playground.repository;

import com.playground.model.VersionedEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VersionedEntityRepository extends CrudRepository<VersionedEntity, UUID> {
}
