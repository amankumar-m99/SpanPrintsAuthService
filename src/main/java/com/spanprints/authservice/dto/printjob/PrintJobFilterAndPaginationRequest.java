package com.spanprints.authservice.dto.printjob;

import java.time.LocalDate;
import java.util.List;

import com.spanprints.authservice.dto.PaginationRequest;
import com.spanprints.authservice.enums.PaymentStatus;
import com.spanprints.authservice.enums.PrintJobStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrintJobFilterAndPaginationRequest {

	private PaginationRequest paginationRequest;

	private Long quantityMin;
	private Long quantityMax;
	private Long totalAmountMin;
	private Long totalAmountMax;
	private Long discountedAmountMin;
	private Long discountedAmountMax;
	private Long pendingAmountMin;
	private Long pendingAmountMax;
	private String bookNumber;
	private String customerName;
	private String customerPhone;
	private LocalDate deliveryDateMin;
	private LocalDate deliveryDateMax;
	private LocalDate placedOnMin;
	private LocalDate placedOnMax;
	private List<Long> jobTypeIds;
	private List<PaymentStatus> paymentStatuses;
	private List<PrintJobStatus> orderStatuses;
}
