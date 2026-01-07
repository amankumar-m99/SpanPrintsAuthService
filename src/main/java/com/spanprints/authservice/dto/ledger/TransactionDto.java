package com.spanprints.authservice.dto.ledger;

import java.time.Instant;

import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDto {

	private Long id;
	private Double amount;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private Instant transactionTime;
	private Long expenseId;
	private Long printJobId;
	private String description;
	private Instant createdAt;
	private Instant updatedAt;

}
