package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInventoryItemRequest {

	@NotNull(message = "Item name is required")
	private String name;
	private String description;
	private BigDecimal rate;
	private Long count;
	private Boolean addToLedger;
}
