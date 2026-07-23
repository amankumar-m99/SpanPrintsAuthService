package com.spanprints.authservice.dto.printjob;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PrintJobDepositAmountRequest {

	@NotNull(message = "Print Job id is required")
	@NotBlank(message = "Print Job id is required")
	private String uuid;

	@NotNull(message = "Deposit amount cannot be empty")
	private BigDecimal depositAmount;

}
