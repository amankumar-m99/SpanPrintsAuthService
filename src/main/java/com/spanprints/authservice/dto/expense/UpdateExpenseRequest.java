package com.spanprints.authservice.dto.expense;

import java.time.Instant;

import com.spanprints.authservice.enums.ExpenseType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseRequest {

	@NotBlank(message = "Expense id is required")
	private Long id;

	@NotBlank(message = "Expense type cannot be empty")
	private ExpenseType expenseType;

	@NotBlank(message = "Amount cannot be empty")
	private Integer amount;

	private String description;

	@NotBlank(message = "Date of expense cannot be empty")
	private Instant dateOfExpense;

}
