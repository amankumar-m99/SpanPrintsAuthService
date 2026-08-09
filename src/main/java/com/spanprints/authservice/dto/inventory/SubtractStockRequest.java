package com.spanprints.authservice.dto.inventory;

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
public class SubtractStockRequest {

	@NotNull(message = "Item id is required")
	private Long itemId;

	@NotNull(message = "Quantity is required")
	private Long quantity;

	private String description;


	@NotNull(message = "Date of transaction cannot be empty")
	private LocalDate dateOfTransaction;
}
