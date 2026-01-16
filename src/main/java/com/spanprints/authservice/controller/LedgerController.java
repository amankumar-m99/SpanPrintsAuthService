package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.ledger.LedgerResponse;
import com.spanprints.authservice.service.LedgerService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

	@Autowired
	private LedgerService ledgerService;

	@GetMapping("/{transactionId}")
	public ResponseEntity<LedgerResponse> getTransaction(
			@PathVariable("transactionId") @NotNull @Positive @Min(1) Long id) {
		LedgerResponse ledgerResponse = new LedgerResponse(ledgerService.getLedgerById(id));
		return new ResponseEntity<>(ledgerResponse, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<LedgerResponse>> getAllTransactions() {
		List<LedgerResponse> list = ledgerService.getAllTransactionsDto().stream().map(LedgerResponse::new).toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> updateLedger() {
		return ledgerService.updateTransaction();
	}

	@DeleteMapping("/{transactionId}")
	public ResponseEntity<SuccessResponseDto> deleteRole(
			@PathVariable("transactionId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = ledgerService.deleteTransactionById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		SuccessResponseDto responseDto = ledgerService.deleteAllTransactions();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
