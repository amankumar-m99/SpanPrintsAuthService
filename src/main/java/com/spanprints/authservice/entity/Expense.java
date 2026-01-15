package com.spanprints.authservice.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spanprints.authservice.enums.ExpenseType;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Expense extends AuditableBaseEntity {

	private ExpenseType expenseType;
	private BigDecimal amount;
	private String description;
	private Instant dateOfExpense;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("createBy")
	public String getAddedBy() {
		return account != null ? account.getUsername() : null;
	}

	@JsonProperty("createdById")
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}

	@OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ledger> ledgers;

	@JsonProperty("ledgerIds")
	public List<Long> getLedgerIds() {
		if (ledgers == null) {
			return Collections.emptyList();
		}
		return ledgers.stream().map(Ledger::getId).toList();
	}

}
