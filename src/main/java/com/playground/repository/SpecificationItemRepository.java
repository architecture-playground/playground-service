package com.playground.repository;

import com.playground.model.SpecificationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecificationItemRepository extends JpaRepository<SpecificationItem, UUID>, JpaSpecificationExecutor<SpecificationItem> {
}
