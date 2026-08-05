package com.spanprints.authservice.dto.expense;

import java.math.BigDecimal;
import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.enums.ExpenseType;

import lombok.Getter;

@Getter
public class ExpenseResponse extends EntityResponseDto {

	private ExpenseType expenseType;
	private BigDecimal amount;
	private String description;
	private Instant dateOfExpense;
	private String createdBy;
	private Long createdById;
	private Long ledgerId;
	private String ledgerUuid;

	public ExpenseResponse(Expense expense) {
		super(expense);
		this.expenseType = expense.getExpenseType();
		this.amount = expense.getAmount();
		this.description = expense.getDescription();
		this.dateOfExpense = expense.getDateOfExpense();
		this.createdBy = expense.getAccount() != null ? expense.getAccount().getUsername() : null;
		this.createdById = expense.getAccount() != null ? expense.getAccount().getId() : null;
		this.ledgerId = expense.getLedger() != null ? expense.getLedger().getId() : null;
		this.ledgerUuid = expense.getLedger() != null ? expense.getLedger().getUuid() : null;
	}
}
