package com.spanprints.authservice.dto.ledger;

import java.math.BigDecimal;
import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.entity.LedgerEntrySource;
import com.spanprints.authservice.entity.LedgerEntryType;

import lombok.Getter;

@Getter
public class LedgerEntryResponse extends EntityResponseDto {

	private BigDecimal amount;
	private LedgerEntryType ledgerEntryType;
	private LedgerEntrySource ledgerEntrySource;
	private Instant transactionDateTime;
	private String description;
	private String printJobUuid;
	private String expenseUuid;
	private String investmentUuid;
	private String addedBy;
	private Long addedById;

	public LedgerEntryResponse(LedgerEntry ledgerEntry) {
		super(ledgerEntry);
		this.amount = ledgerEntry.getAmount();
		this.ledgerEntryType = ledgerEntry.getLedgerEntryType();
		this.ledgerEntrySource = ledgerEntry.getLedgerEntrySource();
		this.transactionDateTime = ledgerEntry.getTransactionDateTime();
		this.printJobUuid = ledgerEntry.getPrintJob() != null ? ledgerEntry.getPrintJob().getUuid() : null;
		this.expenseUuid = ledgerEntry.getExpense() != null ? ledgerEntry.getExpense().getUuid() : null;
		this.investmentUuid = ledgerEntry.getInvestment() != null ? ledgerEntry.getInvestment().getUuid() : null;
		this.addedBy = ledgerEntry.getAccount() != null ? ledgerEntry.getAccount().getUsername() : null;
		this.addedById = ledgerEntry.getAccount() != null ? ledgerEntry.getAccount().getId() : null;
	}

}
