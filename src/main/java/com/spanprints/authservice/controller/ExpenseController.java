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
import com.spanprints.authservice.dto.expense.UpdateExpenseRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.service.ExpenseService;
import com.spanprints.authservice.service.LedgerService;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private LedgerService ledgerService;
	@Autowired
	private SecurityUtils securityUtils;

	@PostMapping("")
	@Transactional
	public Expense createExpense(@Valid @RequestBody CreateExpenseRequest request) {
		Account account = securityUtils.getRequestingAccount();
		Expense expense = expenseService.createExpense(request, account);
		ledgerService.addTransaction(expense);
		return expense;
	}

	@PutMapping("")
	public Expense updateExpense(@Valid @RequestBody UpdateExpenseRequest request) {
		Expense expense = expenseService.updateExpense(request);
		ledgerService.addTransaction(expense);
		return expense;
	}

	@GetMapping("")
	public List<Expense> getAllExpenses() {
		return expenseService.getAllExpenses();
	}

	@GetMapping("/id/{expenseId}")
	public Expense getExpenseById(@PathVariable("expenseId") Long id) {
		return expenseService.getExpenseById(id);
	}
}
