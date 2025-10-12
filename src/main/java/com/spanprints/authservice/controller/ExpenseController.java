package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.ExpenseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.service.ExpenseService;
import com.spanprints.authservice.service.LedgerService;
import com.spanprints.authservice.util.SecurityUtils;

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
	public Expense addExpense(@Valid @RequestBody ExpenseDto dto) {
		Account account = securityUtils.getActiveAccount();
		Expense expense = expenseService.addExpense(dto, account);
		ledgerService.addTransaction(expense);
		return expense;
	}

	@GetMapping("")
	public List<Expense> getAllExpenses() {
		return expenseService.getAllExpenses();
	}
}
