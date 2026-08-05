package com.spanprints.authservice.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spanprints.authservice.enums.ExpenseType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Expense extends AuditableBaseEntity {

	private ExpenseType expenseType;
	private BigDecimal amount;
	private String description;
	private Instant dateOfExpense;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private LedgerEntry ledger;

}
