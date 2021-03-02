package com.playground.repository;

import com.playground.model.DynamicUpdateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaygroundItemRepository extends JpaRepository<DynamicUpdateItem, UUID> {
}
