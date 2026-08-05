package com.spanprints.authservice.dto.investment;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvestmentRequest {

	@NotNull(message = "Amount cannot be empty")
	private BigDecimal amount;

	private String description;

	@NotNull(message = "Date of investment cannot be empty")
	private LocalDate dateOfInvestment;

}
