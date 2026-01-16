package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.enums.InventoryAction;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class InventoryHistoryResponse extends EntityResponseDto {

	private Long inventoryItemId;
	private Long vendorId;
	private BigDecimal rate;
	private BigDecimal amountPaid;
	private Long quantity; // delta (+ / -)

	@Enumerated(EnumType.STRING)
	private InventoryAction action;

	protected InventoryHistoryResponse(InventoryHistory inventoryHistory) {
		super(inventoryHistory);
		this.rate = inventoryHistory.getRate();
		this.amountPaid = inventoryHistory.getAmountPaid();
		this.quantity = inventoryHistory.getQuantity();
		this.inventoryItemId = inventoryHistory.getInventoryItem() != null ? inventoryHistory.getInventoryItem().getId()
				: null;
		this.vendorId = inventoryHistory.getInventoryItem() != null ? inventoryHistory.getVendor().getId() : null;
	}

}
