package com.spanprints.authservice.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.spanprints.authservice.dto.PaginationRequest;
import com.spanprints.authservice.enums.ExpenseType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseFilterAndPaginationRequest {

	private PaginationRequest paginationRequest;
	private LocalDate dateOfExpenseFrom;
	private LocalDate dateOfExpenseTo;
	private List<ExpenseType> expenseTypes;
	private String description;
	private BigDecimal amountMin;
	private BigDecimal amountMax;

}
