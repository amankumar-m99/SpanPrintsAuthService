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
import com.spanprints.authservice.entity.LedgerSource;
import com.spanprints.authservice.entity.LedgerType;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.LedgerEntryRepository;

@Service
public class LedgerEntryService {

	@Autowired
	private LedgerEntryRepository ledgerEntryRepository;

	public LedgerEntry createLedgerEntry(Expense expense) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(expense.getAmount()).ledgerType(LedgerType.DEBIT)
				.ledgerSource(LedgerSource.PURCHASE).transactionDateTime(expense.getDateOfExpense()).printJob(null)
				.expense(expense).account(expense.getAccount()).build();
		return ledgerEntryRepository.save(ledgerEntry);
	}

	public LedgerEntry createLedgerEntry(PrintJob printJob) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(printJob.getDepositAmount()).ledgerType(LedgerType.CREDIT)
				.ledgerSource(LedgerSource.ORDER).transactionDateTime(Instant.now()).printJob(printJob).expense(null)
				.account(printJob.getAccount()).build();
		return ledgerEntryRepository.save(ledgerEntry);
	}

	public LedgerEntry getLedgerEntryById(Long id) {
		return ledgerEntryRepository.findById(id).orElseThrow(
				() -> new TransactionNotFoundException(String.format("No transaction exists with id `%d`", id)));
	}

	public List<LedgerEntry> getAllLedgerEntry() {
		return ledgerEntryRepository.findAll();
	}

	public ResponseEntity<String> updateTransaction() {
		return new ResponseEntity<>("Functionality not implemented yet.", HttpStatus.NOT_IMPLEMENTED);
	}

	public void deleteLedgerEntry(LedgerEntry ledgerEntry) {
		ledgerEntryRepository.delete(ledgerEntry);
	}

	public SuccessResponseDto deleteLedgerEntryById(Long id) {
		ledgerEntryRepository.delete(getLedgerEntryById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted transaction by id `%d`", id));
	}

	public SuccessResponseDto deleteAllLedgerEntry() {
		ledgerEntryRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all transactions.");
	}

}
