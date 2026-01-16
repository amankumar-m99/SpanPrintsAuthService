package com.spanprints.authservice.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spanprints.authservice.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PrintJob extends AuditableBaseEntity {

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

	@OneToOne(mappedBy = "printJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private InventoryHistory inventoryHistory;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@ManyToOne
	@JoinColumn(name = "job_type_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJobType jobType;

	@OneToMany(mappedBy = "printJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FileAttachment> attachments;

	@OneToMany(mappedBy = "printJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ledger> ledgers;

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@JsonIgnore
	private Customer customer;

}
