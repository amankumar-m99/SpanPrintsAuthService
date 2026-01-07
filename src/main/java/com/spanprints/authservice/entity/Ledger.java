package com.spanprints.authservice.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ledger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double amount;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private Instant transactionDate;
	private Instant transactionTime;
	private Instant createdAt;
	private Instant updatedAt;

	@ManyToOne
	@JoinColumn(name = "printjob_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJob printJob;

	@JsonProperty("printJobId")
	public Long getPrintJobId() {
		return printJob != null ? printJob.getId() : null;
	}

	@ManyToOne
	@JoinColumn(name = "expense_id", referencedColumnName = "id")
	@JsonIgnore
	private Expense expense;

	@JsonProperty("expenseId")
	public Long getExpenseId() {
		return expense != null ? expense.getId() : null;
	}

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("addedBy")
	public String getAddedBy() {
		return account != null ? account.getUsername() : null;
	}

	@JsonProperty("addedById")
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}
}
