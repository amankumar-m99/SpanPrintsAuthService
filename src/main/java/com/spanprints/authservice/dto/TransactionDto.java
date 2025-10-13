package com.spanprints.authservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private Integer amount;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private LocalDate transactionDate;
	private LocalDateTime transactionTime;
	private Long expenseId;
	private Long printJobId;
	private String description;

}
