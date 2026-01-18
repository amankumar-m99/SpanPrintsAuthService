package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.expense.CreateExpenseRequest;
import com.spanprints.authservice.dto.expense.ExpenseResponse;
import com.spanprints.authservice.dto.expense.UpdateExpenseRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.service.ExpenseService;
import com.spanprints.authservice.service.LedgerEntryService;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private LedgerEntryService ledgerEntryService;

	private SecurityUtils securityUtils;

	@PostMapping
	@Transactional
	public ExpenseResponse createExpense(@Valid @RequestBody CreateExpenseRequest request) {
		Account account = securityUtils.getRequestingAccount();
		Expense expense = expenseService.createExpense(request, account);
		ledgerEntryService.createLedgerEntry(expense);
		return new ExpenseResponse(expense);
	}

	@GetMapping
	public List<ExpenseResponse> getAllExpenses() {
		List<ExpenseResponse> list = expenseService.getAllExpenses().stream().map(ExpenseResponse::new).toList();
		return list;
	}

	@GetMapping("/id/{id}")
	public ExpenseResponse getExpenseById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		return new ExpenseResponse(expenseService.getExpenseById(id));
	}

	@GetMapping("/uuid/{uuid}")
	public ExpenseResponse getExpenseByUuid(@PathVariable @NotNull String uuid) {
		return new ExpenseResponse(expenseService.getExpenseByUuid(uuid));
	}

	@PutMapping("/id/{id}")
	public ExpenseResponse updateExpenseById(@PathVariable @NotNull @Positive @Min(1) Long id,
			@Valid @RequestBody UpdateExpenseRequest request) {
		Expense expense = expenseService.updateExpenseById(id, request);
		ledgerEntryService.createLedgerEntry(expense);
		return new ExpenseResponse(expense);
	}

	@PutMapping("/uuid/{uuid}")
	public ExpenseResponse updateExpenseByUuid(@PathVariable @NotNull String uuid,
			@Valid @RequestBody UpdateExpenseRequest request) {
		Expense expense = expenseService.updateExpenseByUuid(uuid, request);
		ledgerEntryService.createLedgerEntry(expense);
		return new ExpenseResponse(expense);
	}
}
