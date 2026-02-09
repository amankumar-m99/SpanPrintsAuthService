package com.spanprints.authservice.dto.printjob;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.spanprints.authservice.enums.PaymentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UpdatePrintJobRequest {

	@NotNull(message = "Print Job id is required")
	private Long id;

	@NotNull(message = "Customer id is required")
	private Long customerId;

	@NotNull(message = "Print job type cannot be empty")
	private Long printJobId;

	@NotNull(message = "Quantity cannot be empty")
	private int quantity;

	@NotNull(message = "Date of delivery cannot be empty")
	private LocalDate dateOfDelivery;

	@NotNull(message = "Book number cannot be empty")
	private int bookNumber;

	@NotNull(message = "Wedding book number cannot be empty")
	private int wBookNumber;

	@NotNull(message = "Total amount cannot be empty")
	private BigDecimal totalAmount;

	@NotNull(message = "Discounted amount cannot be empty")
	private BigDecimal discountedAmount;

	@NotNull(message = "Deposit amount cannot be empty")
	private BigDecimal depositAmount;

	@NotNull(message = "Pending amount cannot be empty")
	private BigDecimal pendingAmount;

	@NotNull(message = "Expense type cannot be empty")
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	private String note;

	private String description;
}
