package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.PaginationResponse;
import com.spanprints.authservice.dto.investment.CreateInvestmentRequest;
import com.spanprints.authservice.dto.investment.InvestmentFilterAndPaginationRequest;
import com.spanprints.authservice.dto.investment.InvestmentResponse;
import com.spanprints.authservice.dto.investment.UpdateInvestmentRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Investment;
import com.spanprints.authservice.service.InvestmentService;
import com.spanprints.authservice.service.LedgerEntryService;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private LedgerEntryService ledgerEntryService;

	@Autowired
	private SecurityUtils securityUtils;

	@PostMapping
	@Transactional
	public InvestmentResponse createInvestment(@Valid @RequestBody CreateInvestmentRequest request) {
		Account account = securityUtils.getRequestingAccount();
		Investment investment = investmentService.createInvestment(request, account);
		ledgerEntryService.createLedgerEntry(investment);
		return new InvestmentResponse(investment);
	}

	@GetMapping
	public List<InvestmentResponse> getAllInvestments() {
		return investmentService.getAllInvestments().stream().map(InvestmentResponse::new).toList();
	}

	@PostMapping("/paginated")
	public PaginationResponse<Investment, InvestmentResponse> getPaginatedCustomers(
			@Valid @RequestBody InvestmentFilterAndPaginationRequest filter) {
		Page<Investment> filteredProductsPaginated = investmentService.getFilteredPaginatedInvestments(filter);
		return new PaginationResponse<>(filteredProductsPaginated, InvestmentResponse::new);
	}

	@GetMapping("/id/{id}")
	public InvestmentResponse getInvestmentById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		return new InvestmentResponse(investmentService.getInvestmentById(id));
	}

	@GetMapping("/uuid/{uuid}")
	public InvestmentResponse getInvestmentByUuid(@PathVariable @NotNull String uuid) {
		return new InvestmentResponse(investmentService.getInvestmentByUuid(uuid));
	}

	@PutMapping
	@Transactional
	public InvestmentResponse updateInvestment(@Valid @RequestBody UpdateInvestmentRequest request) {
		Investment investment = investmentService.updateInvestment(request);
		ledgerEntryService.updateTransaction(investment);
		return new InvestmentResponse(investment);
	}
}
