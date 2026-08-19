package com.spanprints.authservice.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class LedgerEntry extends AuditableBaseEntity {
	private BigDecimal amount;
	private LedgerEntryType ledgerEntryType;
	private LedgerEntrySource ledgerEntrySource;
	private Instant transactionDateTime;

	@ManyToOne
	@JoinColumn(name = "printjob_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJob printJob;

	@OneToOne
	@JoinColumn(name = "expense_id", referencedColumnName = "id")
	@JsonIgnore
	private Expense expense;

	@OneToOne
	@JoinColumn(name = "investment_id", referencedColumnName = "id")
	@JsonIgnore
	private Investment investment;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

}
