package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.expense.CreateExpenseRequest;
import com.spanprints.authservice.dto.expense.UpdateExpenseRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.ExpenseRepository;
import com.spanprints.authservice.util.BasicUtils;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	public Expense createExpense(CreateExpenseRequest request, Account account) {
		Expense expense = Expense.builder().expenseType(request.getExpenseType()).amount(request.getAmount())
				.description(request.getDescription())
				.dateOfExpense(BasicUtils.convertLocalDateToInstant(request.getDateOfExpense())).account(account)
				.build();
		return expenseRepository.save(expense);
	}

	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll();
	}

	public Expense getExpenseById(Long id) {
		return expenseRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("No expense found by given id."));
	}

	public Expense getExpenseByUuid(String uuid) {
		return expenseRepository.findByUuid(uuid)
				.orElseThrow(() -> new TransactionNotFoundException("No expense found by given uuid."));
	}

	public Expense updateExpenseById(Long id, UpdateExpenseRequest request) {
		Expense expense = getExpenseById(id);
		return updateExpense(expense, request);
	}

	public Expense updateExpenseByUuid(String uuid, UpdateExpenseRequest request) {
		Expense expense = getExpenseByUuid(uuid);
		return updateExpense(expense, request);
	}

	private Expense updateExpense(Expense expense, UpdateExpenseRequest request) {
		expense.setExpenseType(request.getExpenseType());
		expense.setAmount(request.getAmount());
		expense.setDescription(request.getDescription());
		expense.setDateOfExpense(BasicUtils.convertLocalDateToInstant(request.getDateOfExpense()));
		return expenseRepository.save(expense);
	}

}
