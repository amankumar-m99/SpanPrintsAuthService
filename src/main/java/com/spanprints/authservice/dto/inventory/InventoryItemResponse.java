package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.spanprints.authservice.entity.InventoryItem;

import lombok.Getter;

@Getter
public class InventoryItemResponse {

	private Long id;
	private String uuid;
	private String name;
	private String description;
	private BigDecimal rate;
	private Long availableCount;
	private Instant updatedAt;
	private Instant createdAt;
	private List<Long> inventoryHistoryIds;

	public InventoryItemResponse(InventoryItem inventoryItem) {
		createResponseDto(inventoryItem);
	}

	private void createResponseDto(InventoryItem inventoryItem) {
		this.id = inventoryItem.getId();
		this.uuid = inventoryItem.getUuid();
		this.name = inventoryItem.getName();
		this.description = inventoryItem.getDescription();
		this.rate = inventoryItem.getRate();
		this.availableCount = inventoryItem.getAvailableCount();
		this.inventoryHistoryIds = getInventoryHistoryIds(inventoryItem);
		this.updatedAt = inventoryItem.getUpdatedAt();
		this.createdAt = inventoryItem.getCreatedAt();
	}

	private List<Long> getInventoryHistoryIds(InventoryItem inventoryItem) {
		if (inventoryItem.getInventoryHistories() == null) {
			return Collections.emptyList();
		}
		return inventoryItem.getInventoryHistories().stream().map(o -> o.getId()).toList();
	}
}
