package com.spanprints.authservice.dto.expense;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.enums.ExpenseType;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

@Getter
public class ExpenseResponse extends EntityResponseDto {

	private ExpenseType expenseType;
	private BigDecimal amount;
	private String description;
	private Instant dateOfExpense;
	private String createdBy;
	private Long createdById;
	private List<Long> ledgerEntryIds;

	public ExpenseResponse(Expense expense) {
		super(expense);
		this.expenseType = expense.getExpenseType();
		this.amount = expense.getAmount();
		this.description = expense.getDescription();
		this.dateOfExpense = expense.getDateOfExpense();
		this.createdBy = expense.getAccount() != null ? expense.getAccount().getUsername() : null;
		this.createdById = expense.getAccount() != null ? expense.getAccount().getId() : null;
		this.ledgerEntryIds = expense.getLedgers() != null ? expense.getLedgers().stream().map(LedgerEntry::getId).toList()
				: Collections.emptyList();
	}
}
