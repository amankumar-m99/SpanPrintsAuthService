package com.spanprints.authservice.entity;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spanprints.authservice.enums.JobType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String customrtName;
	private String phone;
	private String address;

	private JobType jobType;
	private int count;
	private int bookNumber;
	private int wBookNumber;
	private int dateOfDelivery;
	private String description;

	private int totalAmount;
	private int depositAmount;
	private int discountedAmount;
	private int pendingAmount;
	private PaymentStatus paymentStatus;

	private String note;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FileAttachment> attachments;

	@JsonProperty("attachmentIds")
	public List<Long> getAttachmentIds() {
		if (attachments == null) {
			return Collections.emptyList();
		}
		List<Long> ids = attachments.stream().map(o -> o.getId()).collect(Collectors.toList());
		return ids;
	}

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ledger> ledgers;

	@JsonProperty("ledgerIds")
	public List<Long> getLedgerIds() {
		if (ledgers == null) {
			return Collections.emptyList();
		}
		List<Long> ids = ledgers.stream().map(o -> o.getId()).collect(Collectors.toList());
		return ids;
	}

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("accountId") // will be included in JSON
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}
}
