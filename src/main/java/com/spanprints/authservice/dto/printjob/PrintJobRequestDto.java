package com.spanprints.authservice.dto.printjob;

import java.time.LocalDate;

import com.spanprints.authservice.enums.JobType;
import com.spanprints.authservice.enums.PaymentStatus;

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
public class PrintJobRequestDto {

	private String customerName;
	private String phone;
	private String address;
	private JobType jobType;
	private int count;
	private LocalDate dateOfDelivery;
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
