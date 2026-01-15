package com.spanprints.authservice.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.spanprints.authservice.enums.ExpenseType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseRequest {

	@NotNull(message = "Expense type cannot be empty")
	@Enumerated(EnumType.STRING)
	private ExpenseType expenseType;

	@NotNull(message = "Amount cannot be empty")
	private BigDecimal amount;

	private String description;

	@NotNull(message = "Date of expense cannot be empty")
	private LocalDate dateOfExpense;

}
