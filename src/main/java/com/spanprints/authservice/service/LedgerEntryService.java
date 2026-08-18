package com.spanprints.authservice.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.ledger.LedgerEntryFilterAndPaginationRequest;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.entity.Investment;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.entity.LedgerEntrySource;
import com.spanprints.authservice.entity.LedgerEntryType;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.LedgerEntryRepository;
import com.spanprints.authservice.specifications.LedgderSpecifications;

@Service
public class LedgerEntryService {

	@Autowired
	private LedgerEntryRepository ledgerEntryRepository;

	public LedgerEntry createLedgerEntry(Investment invesment) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(invesment.getAmount())
				.ledgerEntryType(LedgerEntryType.CREDIT).ledgerEntrySource(LedgerEntrySource.INVESTMENT)
				.transactionDateTime(invesment.getDateOfInvestment()).investment(invesment)
				.account(invesment.getAccount()).build();
		return ledgerEntryRepository.save(ledgerEntry);
	}

	public LedgerEntry createLedgerEntry(Expense expense) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(expense.getAmount())
				.ledgerEntryType(LedgerEntryType.DEBIT).ledgerEntrySource(LedgerEntrySource.PURCHASE)
				.transactionDateTime(expense.getDateOfExpense()).expense(expense).account(expense.getAccount()).build();
		return ledgerEntryRepository.save(ledgerEntry);
	}

	public LedgerEntry createLedgerEntry(PrintJob printJob, BigDecimal amount, Instant instant) {
		LedgerEntry ledgerEntry = LedgerEntry.builder().amount(amount).ledgerEntryType(LedgerEntryType.CREDIT)
				.ledgerEntrySource(LedgerEntrySource.ORDER).transactionDateTime(instant).printJob(printJob)
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

	public Page<LedgerEntry> getFilteredPaginatedLedgerEntry(LedgerEntryFilterAndPaginationRequest filter) {
		Specification<LedgerEntry> spec = LedgderSpecifications.withFilter(filter);
		Pageable pageable = PageRequest.of(filter.getPaginationRequest().getPageNumber(),
				filter.getPaginationRequest().getPageSize());
		return ledgerEntryRepository.findAll(spec, pageable);
	}

	public LedgerEntry updateTransaction(Expense expense) {
		LedgerEntry ledger = expense.getLedger();
		ledger.setTransactionDateTime(expense.getDateOfExpense());
		return ledgerEntryRepository.save(ledger);
	}

	public LedgerEntry updateTransaction(Investment investment) {
		LedgerEntry ledger = investment.getLedger();
		ledger.setTransactionDateTime(investment.getDateOfInvestment());
		return ledgerEntryRepository.save(ledger);
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
