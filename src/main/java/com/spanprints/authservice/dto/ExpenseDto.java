package com.spanprints.authservice.dto;

import java.time.LocalDate;

import com.spanprints.authservice.enums.ExpenseType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExpenseDto {

	@NotNull
	public ExpenseType expenseType;
	@NotNull
	public Integer amount;
	public String description;
	@NotNull
	public LocalDate dateOfExpense;

}
 