package com.spanprints.authservice.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spanprints.authservice.enums.PaymentStatus;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class PrintJob {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int count;
	private int bookNumber;
	private int wBookNumber;
	private Instant dateOfDelivery;

	private int totalAmount;
	private int discountedAmount;
	private int depositAmount;
	private int pendingAmount;
	private PaymentStatus paymentStatus;

	private String note;
	private String description;
	private Instant updateAt;
	private Instant createdAt;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("createdBy")
	public String getAccountUsername() {
		return account != null ? account.getUsername() : null;
	}

	@JsonProperty("createdById")
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJobType jobType;

	@JsonProperty("jobTypeId")
	public Long getJobTypeId() {
		return jobType != null ? jobType.getId() : null;
	}

	@JsonProperty("jobTypeName")
	public String getJobTypeName() {
		return jobType != null ? jobType.getName() : null;
	}

	@JsonProperty("jobTypeDescription")
	public String getJobTypeDescription() {
		return jobType != null ? jobType.getDescription() : null;
	}

	@OneToMany(mappedBy = "printJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FileAttachment> attachments;

	@JsonProperty("attachmentIds")
	public List<Long> getAttachmentIds() {
		if (attachments == null) {
			return Collections.emptyList();
		}
		return attachments.stream().map(FileAttachment::getId).toList();
	}

	@OneToMany(mappedBy = "printJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ledger> ledgers;

	@JsonProperty("ledgerIds")
	public List<Long> getLedgerIds() {
		if (ledgers == null) {
			return Collections.emptyList();
		}
		return ledgers.stream().map(Ledger::getId).toList();
	}

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@JsonIgnore
	private Customer customer;

	@JsonProperty("customerId")
	public Long getCustomerId() {
		return customer != null ? customer.getId() : null;
	}

	@JsonProperty("customerName")
	public String getCustomerName() {
		return customer != null ? customer.getName() : null;
	}

	@JsonProperty("customerPhone")
	public String getCustomerPhone() {
		return customer != null ? customer.getPrimaryPhoneNumber() : null;
	}

	@JsonProperty("customerAddress")
	public String getCustomerAddress() {
		return customer != null ? customer.getAddress() : null;
	}
}
