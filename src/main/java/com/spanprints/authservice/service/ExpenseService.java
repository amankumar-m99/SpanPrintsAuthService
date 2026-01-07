package com.spanprints.authservice.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.expense.CreateExpenseRequest;
import com.spanprints.authservice.dto.expense.UpdateExpenseRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	public Expense createExpense(CreateExpenseRequest request, Account account) {
		Expense expense = Expense.builder().id(null).expenseType(request.getExpenseType()).amount(request.getAmount())
				.description(request.getDescription()).dateOfExpense(request.getDateOfExpense())
				.createdAt(Instant.now()).updatedAt(Instant.now()).account(account).build();
		return expenseRepository.save(expense);
	}

	public Expense updateExpense(UpdateExpenseRequest request) {
		Expense expense = getExpenseById(request.getId());
		expense.setExpenseType(request.getExpenseType());
		expense.setAmount(request.getAmount());
		expense.setDescription(request.getDescription());
		expense.setDateOfExpense(request.getDateOfExpense());
		expense.setUpdatedAt(Instant.now());
		return expenseRepository.save(expense);
	}

	public Expense getExpenseById(Long id) {
		return expenseRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("No expense found by given id."));
	}

	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll();
	}

}
