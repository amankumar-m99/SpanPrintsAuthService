package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.PaginationResponse;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.ledger.LedgerEntryFilterAndPaginationRequest;
import com.spanprints.authservice.dto.ledger.LedgerEntryResponse;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.service.LedgerEntryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/ledgerentries")
public class LedgerController {

	@Autowired
	private LedgerEntryService ledgerEntryService;

	@GetMapping("/{transactionId}")
	public ResponseEntity<LedgerEntryResponse> getLedgerEntryById(
			@PathVariable("transactionId") @NotNull @Positive @Min(1) Long id) {
		LedgerEntryResponse ledgerEntryResponse = new LedgerEntryResponse(ledgerEntryService.getLedgerEntryById(id));
		return new ResponseEntity<>(ledgerEntryResponse, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<LedgerEntryResponse>> getAllLedgerEntry() {
		List<LedgerEntryResponse> list = ledgerEntryService.getAllLedgerEntry().stream().map(LedgerEntryResponse::new)
				.toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/paginated")
	public PaginationResponse<LedgerEntry, LedgerEntryResponse> getPaginatedCustomers(
			@Valid @RequestBody LedgerEntryFilterAndPaginationRequest filter) {
		Page<LedgerEntry> filteredProductsPaginated = ledgerEntryService.getFilteredPaginatedLedgerEntry(filter);
		return new PaginationResponse<>(filteredProductsPaginated, LedgerEntryResponse::new);
	}

	@PutMapping
	public ResponseEntity<String> updateLedgerEntry() {
		return new ResponseEntity<>("Functionality not implemented yet.", HttpStatus.NOT_IMPLEMENTED);
	}

	@DeleteMapping("/{transactionId}")
	public ResponseEntity<SuccessResponseDto> deleteLedgerEntryById(
			@PathVariable("transactionId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = ledgerEntryService.deleteLedgerEntryById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllLedgerEntry() {
		SuccessResponseDto responseDto = ledgerEntryService.deleteAllLedgerEntry();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
