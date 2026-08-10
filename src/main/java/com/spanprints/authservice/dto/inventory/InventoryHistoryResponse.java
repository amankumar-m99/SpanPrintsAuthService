package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;
import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.enums.InventoryAction;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class InventoryHistoryResponse extends EntityResponseDto {

	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private InventoryAction action;

	private Instant dateOfTransaction;
	private Long quantity; // delta (+ / -)
	private String description;
	private Long vendorId;
	private Long inventoryItemId;

	public InventoryHistoryResponse(InventoryHistory inventoryHistory) {
		super(inventoryHistory);
		this.amount = inventoryHistory.getAmount();
		this.action = inventoryHistory.getAction();
		this.dateOfTransaction = inventoryHistory.getTransactionDateTime();
		this.quantity = inventoryHistory.getQuantity();
		this.description = inventoryHistory.getDescription();
		this.inventoryItemId = inventoryHistory.getInventoryItem() != null ? inventoryHistory.getInventoryItem().getId()
				: null;
		this.vendorId = inventoryHistory.getVendor() != null ? inventoryHistory.getVendor().getId() : null;
	}

}
