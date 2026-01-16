package com.spanprints.authservice.dto.ledger;

import java.math.BigDecimal;
import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Ledger;
import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;

import lombok.Getter;

@Getter
public class LedgerResponse extends EntityResponseDto {

	private BigDecimal amount;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private Instant transactionDateTime;
	private String description;
	private Long printJobId;
	private Long expenseId;
	private String addedBy;
	private Long addedById;

	public LedgerResponse(Ledger ledger) {
		super(ledger);
		this.amount = ledger.getAmount();
		this.transactionType = ledger.getTransactionType();
		this.transactionDomain = ledger.getTransactionDomain();
		this.transactionDateTime = ledger.getTransactionDateTime();
		this.description = ledger.getDescription();
		this.printJobId = ledger.getPrintJob() != null ? ledger.getPrintJob().getId() : null;
		this.expenseId = ledger.getExpense() != null ? ledger.getExpense().getId() : null;
		this.addedBy = ledger.getAccount() != null ? ledger.getAccount().getUsername() : null;
		this.addedById = ledger.getAccount() != null ? ledger.getAccount().getId() : null;
	}

}
