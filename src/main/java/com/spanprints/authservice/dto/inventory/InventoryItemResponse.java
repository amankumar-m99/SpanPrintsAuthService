package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.entity.InventoryItem;

import lombok.Getter;

@Getter
public class InventoryItemResponse extends EntityResponseDto {

	private String name;
	private String code;
	private String description;
	private BigDecimal rate;
	private Long quantity;
	private List<Long> inventoryHistoryIds;

	public InventoryItemResponse(InventoryItem inventoryItem) {
		super(inventoryItem);
		this.name = inventoryItem.getName();
		this.code = inventoryItem.getCode();
		this.description = inventoryItem.getDescription();
		this.rate = inventoryItem.getRate();
		this.quantity = inventoryItem.getQuantity();
		this.inventoryHistoryIds = inventoryItem.getInventoryHistories() != null
				? inventoryItem.getInventoryHistories().stream().map(InventoryHistory::getId).toList()
				: Collections.emptyList();
	}
}
