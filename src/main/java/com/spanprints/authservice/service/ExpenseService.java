package com.spanprints.authservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.ExpenseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	public Expense addExpense(ExpenseDto dto, Account account) {
		Expense expense = new Expense(null, dto.expenseType, dto.amount, dto.description, dto.dateOfExpense,
				LocalDateTime.now(), null, account);
		return expenseRepository.save(expense);
	}

	public Expense getExpenseById(Long id) {
		return expenseRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("No expense found by given id."));
	}

	public List<Expense> getAllExpenses(){
		return expenseRepository.findAll();
	}

}
