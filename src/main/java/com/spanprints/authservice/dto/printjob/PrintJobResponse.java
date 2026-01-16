package com.spanprints.authservice.dto.printjob;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.FileAttachment;
import com.spanprints.authservice.entity.Ledger;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.enums.PaymentStatus;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

@Getter
public class PrintJobResponse extends EntityResponseDto {

	private Integer count;
	private Integer bookNumber;
	private Integer wBookNumber;
	private Instant dateOfDelivery;

	private BigDecimal totalAmount;
	private BigDecimal discountedAmount;
	private BigDecimal depositAmount;
	private BigDecimal pendingAmount;
	private PaymentStatus paymentStatus;

	private String note;
	private String description;

	private String createdBy;
	private Long createdById;

	private Long jobTypeId;
	private String jobTypeName;

	private List<Long> attachmentIds;
	private List<Long> ledgerIds;
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String customerAddress;

	public PrintJobResponse(PrintJob printJob) {
		super(printJob);
		this.count = printJob.getCount();
		this.bookNumber = printJob.getBookNumber();
		this.wBookNumber = printJob.getWBookNumber();
		this.dateOfDelivery = printJob.getDateOfDelivery();
		this.totalAmount = printJob.getTotalAmount();
		this.discountedAmount = printJob.getDiscountedAmount();
		this.depositAmount = printJob.getDepositAmount();
		this.pendingAmount = printJob.getPendingAmount();
		this.paymentStatus = printJob.getPaymentStatus();
		this.note = printJob.getNote();
		this.description = printJob.getDescription();
		if (printJob.getAccount() != null) {
			this.createdBy = printJob.getAccount().getUsername();
			this.createdById = printJob.getAccount().getId();
		}
		if (printJob.getJobType() != null) {
			this.jobTypeId = printJob.getJobType().getId();
			this.jobTypeName = printJob.getJobType().getName();
		}
		this.attachmentIds = printJob.getAttachments() != null
				? printJob.getAttachments().stream().map(FileAttachment::getId).toList()
				: Collections.emptyList();
		this.ledgerIds = printJob.getLedgers() != null ? printJob.getLedgers().stream().map(Ledger::getId).toList()
				: Collections.emptyList();
		if (printJob.getCustomer() != null) {
			this.customerId = printJob.getCustomer().getId();
			this.customerName = printJob.getCustomer().getName();
			this.customerAddress = printJob.getCustomer().getAddress();
		}
	}

}
