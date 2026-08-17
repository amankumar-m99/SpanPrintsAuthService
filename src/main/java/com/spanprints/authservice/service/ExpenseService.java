package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.expense.CreateExpenseRequest;
import com.spanprints.authservice.dto.expense.ExpenseFilterAndPaginationRequest;
import com.spanprints.authservice.dto.expense.UpdateExpenseRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.exception.InvalidInputsException;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.ExpenseRepository;
import com.spanprints.authservice.specifications.ExpenseSpecifications;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.validation.Valid;

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

	public Page<Expense> getFilteredPaginatedExpenses(@Valid ExpenseFilterAndPaginationRequest filter) {
		Specification<Expense> spec = ExpenseSpecifications.withFilter(filter);
		// Returns a chunk of data with metadata (total pages, total items)
		Pageable pageable = PageRequest.of(filter.getPaginationRequest().getPageNumber(),
				filter.getPaginationRequest().getPageSize());
		return expenseRepository.findAll(spec, pageable);
	}

	public Expense getExpenseById(Long id) {
		return expenseRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("No expense found by given id."));
	}

	public Expense getExpenseByUuid(String uuid) {
		return expenseRepository.findByUuid(uuid)
				.orElseThrow(() -> new TransactionNotFoundException("No expense found by given uuid."));
	}

	public Expense updateExpense(UpdateExpenseRequest request) {
		Expense expense = null;
		if (!BasicUtils.isNullOrBlank(request.getUuid())) {
			expense = getExpenseByUuid(request.getUuid());
		} else if (request.getId() != null) {
			expense = getExpenseById(request.getId());
		} else {
			throw new InvalidInputsException("Provide id or uuid");
		}
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
