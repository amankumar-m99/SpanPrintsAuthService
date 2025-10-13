package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.TransactionDto;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.entity.Ledger;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.LedgerRepository;

@Service
public class LedgerService {

	@Autowired
	private LedgerRepository ledgerRepository;

	public Ledger addTransaction(Expense expense) {
		Ledger ledger = Ledger.builder().amount(expense.getAmount()).transactionType(TransactionType.DEBIT)
				.transactionDomain(TransactionDomain.EXPENSE).transactionDate(expense.getDateOfExpense()).printJob(null)
				.expense(expense).account(expense.getAccount()).build();
		return ledgerRepository.save(ledger);
	}

	public Ledger getTransactionById(Long id) {
		return ledgerRepository.findById(id).orElseThrow(
				() -> new TransactionNotFoundException(String.format("No transaction exists with id `%d`", id)));
	}

	public TransactionDto getTransactionDtoById(Long id) {
		Ledger ledger = getTransactionById(id);
		return convertLedgerTransactionToDto(ledger);
	}

	public List<Ledger> getAllTransactions() {
		return ledgerRepository.findAll();
	}

	public List<TransactionDto> getAllTransactionsDto() {
		return ledgerRepository.findAll().stream().map(e -> convertLedgerTransactionToDto(e)).toList();
	}

	public Ledger updateTransaction() {
		return null;
	}

	public void deleteTransaction(Ledger ledger) {
		ledgerRepository.delete(ledger);
	}

	public SuccessResponseDto deleteTransactionById(Long id) {
		ledgerRepository.delete(getTransactionById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted transaction by id `%d`", id));
	}

	public SuccessResponseDto deleteAllTransactions() {
		ledgerRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all transactions.");
	}

	public TransactionDto convertLedgerTransactionToDto(Ledger ledger) {
		String description = null;
		switch (ledger.getTransactionDomain()) {
		case EXPENSE:
			description = ledger.getExpense().getDescription();
			break;
		case PRINT_JOB:
			PrintJob printJob = ledger.getPrintJob();
			description = String.format("%d | %s | %d", printJob.getId(), printJob.getJobType(), printJob.getCount());
			break;
		}
		return TransactionDto.builder().id(ledger.getId()).amount(ledger.getAmount())
				.transactionType(ledger.getTransactionType()).transactionDomain(ledger.getTransactionDomain())
				.transactionDate(ledger.getTransactionDate()).transactionTime(ledger.getTransactionTime())
				.expenseId(ledger.getExpenseId()).printJobId(ledger.getPrintJobId()).description(description).build();
	}

}
