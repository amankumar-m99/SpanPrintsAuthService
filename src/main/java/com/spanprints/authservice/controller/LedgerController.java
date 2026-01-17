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
import com.spanprints.authservice.dto.ledger.LedgerEntryResponse;
import com.spanprints.authservice.service.LedgerEntryService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/ledgerentries")
public class LedgerController {

	@Autowired
	private LedgerEntryService ledgerEntryService;

	@GetMapping("/{transactionId}")
	public ResponseEntity<LedgerEntryResponse> getTransaction(
			@PathVariable("transactionId") @NotNull @Positive @Min(1) Long id) {
		LedgerEntryResponse ledgerEntryResponse = new LedgerEntryResponse(ledgerEntryService.getLedgerEntryById(id));
		return new ResponseEntity<>(ledgerEntryResponse, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<LedgerEntryResponse>> getAllTransactions() {
		List<LedgerEntryResponse> list = ledgerEntryService.getAllLedgerEntry().stream().map(LedgerEntryResponse::new)
				.toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> updateLedger() {
		return ledgerEntryService.updateTransaction();
	}

	@DeleteMapping("/{transactionId}")
	public ResponseEntity<SuccessResponseDto> deleteRole(
			@PathVariable("transactionId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = ledgerEntryService.deleteLedgerEntryById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllRoles() {
		SuccessResponseDto responseDto = ledgerEntryService.deleteAllLedgerEntry();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
