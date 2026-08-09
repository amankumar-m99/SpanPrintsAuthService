package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddStockRequest {

	@NotNull(message = "Item id is required")
	private Long itemId;

	@NotNull(message = "Quantity is required")
	private Long quantity;

	@NotNull(message = "Amount is required")
	private BigDecimal amountPaid;

	private Long vendorId;
	private String description;
	private Boolean addToLedger;

	@NotNull(message = "Date of transaction cannot be empty")
	private LocalDate dateOfTransaction;
}
