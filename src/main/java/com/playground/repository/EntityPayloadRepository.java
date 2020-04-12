package com.playground.repository;

import com.playground.model.EntityPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntityPayloadRepository extends JpaRepository<EntityPayload, UUID> {
}
