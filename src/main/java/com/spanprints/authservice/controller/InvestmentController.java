package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.investment.CreateInvestmentRequest;
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
		List<InvestmentResponse> list = investmentService.getAllInvestments().stream().map(InvestmentResponse::new).toList();
		return list;
	}

	@GetMapping("/id/{id}")
	public InvestmentResponse getInvestmentById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		return new InvestmentResponse(investmentService.getInvestmentById(id));
	}

	@GetMapping("/uuid/{uuid}")
	public InvestmentResponse getInvestmentByUuid(@PathVariable @NotNull String uuid) {
		return new InvestmentResponse(investmentService.getInvestmentByUuid(uuid));
	}

	@PutMapping("/id/{id}")
	@Transactional
	public InvestmentResponse updateInvestmentById(@PathVariable @NotNull @Positive @Min(1) Long id,
			@Valid @RequestBody UpdateInvestmentRequest request) {
		Investment investment = investmentService.updateInvestmentById(id, request);
		ledgerEntryService.updateTransaction(investment);
		return new InvestmentResponse(investment);
	}

	@PutMapping("/uuid/{uuid}")
	@Transactional
	public InvestmentResponse updateInvestmentByUuid(@PathVariable @NotNull String uuid,
			@Valid @RequestBody UpdateInvestmentRequest request) {
		Investment investment = investmentService.updateInvestmentByUuid(uuid, request);
		ledgerEntryService.updateTransaction(investment);
		return new InvestmentResponse(investment);
	}
}
