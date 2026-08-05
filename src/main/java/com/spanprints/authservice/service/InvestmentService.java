package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.investment.CreateInvestmentRequest;
import com.spanprints.authservice.dto.investment.UpdateInvestmentRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Investment;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.InvestmentRepository;
import com.spanprints.authservice.util.BasicUtils;

@Service
public class InvestmentService {

	@Autowired
	private InvestmentRepository investmentRepository;

	public Investment createInvestment(CreateInvestmentRequest request, Account account) {
		Investment investment = Investment.builder().amount(request.getAmount()).description(request.getDescription())
				.dateOfInvestment(BasicUtils.convertLocalDateToInstant(request.getDateOfInvestment())).account(account)
				.build();
		return investmentRepository.save(investment);
	}

	public List<Investment> getAllInvestments() {
		return investmentRepository.findAll();
	}

	public Investment getInvestmentById(Long id) {
		return investmentRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("No investment found by given id."));
	}

	public Investment getInvestmentByUuid(String uuid) {
		return investmentRepository.findByUuid(uuid)
				.orElseThrow(() -> new TransactionNotFoundException("No investment found by given uuid."));
	}

	public Investment updateInvestmentById(Long id, UpdateInvestmentRequest request) {
		Investment investment = getInvestmentById(id);
		return updateInvestment(investment, request);
	}

	public Investment updateInvestmentByUuid(String uuid, UpdateInvestmentRequest request) {
		Investment investment = getInvestmentByUuid(uuid);
		return updateInvestment(investment, request);
	}

	private Investment updateInvestment(Investment investment, UpdateInvestmentRequest request) {
		investment.setAmount(request.getAmount());
		investment.setDescription(request.getDescription());
		investment.setDateOfInvestment(BasicUtils.convertLocalDateToInstant(request.getDateOfInvestment()));
		return investmentRepository.save(investment);
	}
}
