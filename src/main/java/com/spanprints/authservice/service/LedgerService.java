package com.spanprints.authservice.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.enums.TransactionDomain;
import com.spanprints.authservice.enums.TransactionType;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.LedgerEntryRepository;

@Service
public class LedgerService {

	@Autowired
	private LedgerEntryRepository ledgerEntryRepository;

	public LedgerEntry addTransaction(Expense expense) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(expense.getAmount()).transactionType(TransactionType.DEBIT)
				.transactionDomain(TransactionDomain.EXPENSE).transactionDateTime(expense.getDateOfExpense())
				.printJob(null).expense(expense).account(expense.getAccount()).build();
		return ledgerEntryRepository.save(ledgerEntry);
	}

	public LedgerEntry addTransaction(PrintJob printJob) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(printJob.getTotalAmount()).transactionType(TransactionType.CREDIT)
				.transactionDomain(TransactionDomain.PRINT_JOB).transactionDateTime(Instant.now()).printJob(printJob)
				.expense(null).account(printJob.getAccount()).build();
		return ledgerEntryRepository.save(ledgerEntry);
	}

	public LedgerEntry getTransactionById(Long id) {
		return ledgerEntryRepository.findById(id).orElseThrow(
				() -> new TransactionNotFoundException(String.format("No transaction exists with id `%d`", id)));
	}

	public LedgerEntry getLedgerById(Long id) {
		return getTransactionById(id);
	}

	public List<LedgerEntry> getAllTransactions() {
		return ledgerEntryRepository.findAll();
	}

	public List<LedgerEntry> getAllTransactionsDto() {
		return ledgerEntryRepository.findAll();
	}

	public ResponseEntity<String> updateTransaction() {
		return new ResponseEntity<>("Functionality not implemented yet.", HttpStatus.NOT_IMPLEMENTED);
	}

	public void deleteTransaction(LedgerEntry ledgerEntry) {
		ledgerEntryRepository.delete(ledgerEntry);
	}

	public SuccessResponseDto deleteTransactionById(Long id) {
		ledgerEntryRepository.delete(getTransactionById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted transaction by id `%d`", id));
	}

	public SuccessResponseDto deleteAllTransactions() {
		ledgerEntryRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all transactions.");
	}

}
