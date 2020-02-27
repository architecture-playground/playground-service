package com.playground.repository;

import com.playground.model.PlaygroundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaygroundEntityRepository extends JpaRepository<PlaygroundEntity, UUID> {
}
