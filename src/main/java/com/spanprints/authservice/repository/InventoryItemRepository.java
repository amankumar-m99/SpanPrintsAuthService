package com.spanprints.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.InventoryItem;

@Repository
public interface InventoryItemRepository
		extends JpaRepository<InventoryItem, Long>, JpaSpecificationExecutor<InventoryItem> {

	Optional<InventoryItem> findByUuid(String uuid);
}
