package com.spanprints.authservice.dto.printjob;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.spanprints.authservice.enums.PrintJobStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UpdatePrintJobNonDependentFieldsRequest {

	@NotNull(message = "Print Job id is required")
	@NotBlank(message = "Print Job id is required")
	private String uuid;

	@NotNull(message = "Print job type cannot be empty")
	private Long printJobTypeId;

	@NotNull(message = "Quantity cannot be empty")
	private int quantity;

	@NotNull(message = "Book number cannot be empty")
	private int bookNumber;

	@NotNull(message = "Date of placed cannot be empty")
	private LocalDate dateOfPlaced;

	@NotNull(message = "Date of delivery cannot be empty")
	private LocalDate dateOfDelivery;

	@NotNull(message = "Total amount cannot be empty")
	private BigDecimal totalAmount;

	@NotNull(message = "Discounted amount cannot be empty")
	private BigDecimal discountedAmount;

	private String note;

	private String description;

	@NotNull(message = "Order status cannot be empty")
	@Enumerated(EnumType.STRING)
	private PrintJobStatus printJobStatus;
}
