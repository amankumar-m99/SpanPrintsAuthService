package com.spanprints.authservice.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;

import jakarta.persistence.Entity;
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
public class Ledger extends AuditableBaseEntity {
	private BigDecimal amount;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private Instant transactionDateTime;
	private String description;

	@ManyToOne
	@JoinColumn(name = "printjob_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJob printJob;

	@ManyToOne
	@JoinColumn(name = "expense_id", referencedColumnName = "id")
	@JsonIgnore
	private Expense expense;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

}
