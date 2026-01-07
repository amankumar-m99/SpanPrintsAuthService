package com.spanprints.authservice.dto.printjob;

import java.time.Instant;

import com.spanprints.authservice.enums.JobType;
import com.spanprints.authservice.enums.PaymentStatus;

import jakarta.validation.constraints.NotBlank;
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
public class PrintJobRequest {

	@NotBlank(message = "Customer id is required")
	private Long customerId;
	private JobType jobType;
	private int count;
	private Instant dateOfDelivery;
	private int totalAmount;
	private int depositAmount;
	private String note;
	private int bookNumber;
	private int wBookNumber;
	private String description;
	private int discountedAmount;
	private int pendingAmount;
	private PaymentStatus paymentStatus;

}
