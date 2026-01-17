package com.spanprints.authservice.dto.ledger;

import java.math.BigDecimal;
import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;

import lombok.Getter;

@Getter
public class LedgerEntryResponse extends EntityResponseDto {

	private BigDecimal amount;
	private TransactionType transactionType;
	private TransactionDomain transactionDomain;
	private Instant transactionDateTime;
	private Long printJobId;
	private Long expenseId;
	private String addedBy;
	private Long addedById;

	public LedgerEntryResponse(LedgerEntry ledgerEntry) {
		super(ledgerEntry);
		this.amount = ledgerEntry.getAmount();
		this.transactionType = ledgerEntry.getTransactionType();
		this.transactionDomain = ledgerEntry.getTransactionDomain();
		this.transactionDateTime = ledgerEntry.getTransactionDateTime();
		this.printJobId = ledgerEntry.getPrintJob() != null ? ledgerEntry.getPrintJob().getId() : null;
		this.expenseId = ledgerEntry.getExpense() != null ? ledgerEntry.getExpense().getId() : null;
		this.addedBy = ledgerEntry.getAccount() != null ? ledgerEntry.getAccount().getUsername() : null;
		this.addedById = ledgerEntry.getAccount() != null ? ledgerEntry.getAccount().getId() : null;
	}

}
