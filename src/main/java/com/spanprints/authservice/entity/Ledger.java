package com.spanprints.authservice.entity;

import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ledger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private int amount;
	private LocalDateTime transactionTime;

	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	@JsonIgnore
	private Order order;

	@ManyToOne
	@JoinColumn(name = "expense_id", referencedColumnName = "id")
	@JsonIgnore
	private Expense expense;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("accountId") // will be included in JSON
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}

}
